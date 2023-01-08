package com.gameloop.laboratorioclinicoproc.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gameloop.laboratorioclinicoproc.database.model.labtest.LabTest
import com.gameloop.laboratorioclinicoproc.database.model.labtestcategory.LabTestCategory
import com.gameloop.laboratorioclinicoproc.database.model.toLabTests
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.*
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import timber.log.Timber

private const val LAB_TEST_CATEGORY_COLLECTION = "LabTestCategory"
private const val LAB_TEST_COLLECTION = "LabTest"

class LabNetworkService {
    private val serviceScope = CoroutineScope(Dispatchers.IO)
    private val db = Firebase.firestore

    companion object {
        @Volatile
        private lateinit var INSTANCE: LabNetworkService
        val instance: LabNetworkService
            get() {
                if (!::INSTANCE.isInitialized) {
                    INSTANCE = LabNetworkService()
                }
                return INSTANCE
            }
    }

    init {
        // Keep latest results cached
        db.firestoreSettings = firestoreSettings {
            isPersistenceEnabled = true
            cacheSizeBytes = FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED
        }
    }

    suspend fun getNonLiveLabTestsByCategory(categoryTitle: String) = withContext(Dispatchers.IO) {
        // Getting category
        val category = db.collection(LAB_TEST_CATEGORY_COLLECTION)
            .whereEqualTo("title", categoryTitle)
            .limit(1)
            .get()
            .await()
            .documents
            .first()
            .reference// getting lab test from cache

        // Getting lab tests
        db.collection(LAB_TEST_COLLECTION)
            .whereEqualTo("category", category)
            .get()
            .await()
            .toLabTests(Source.CACHE)
    }

    fun getLabTestsByCategory(categoryTitle: String): LiveData<List<LabTest>> {
        val testsByCategory = MutableLiveData<List<LabTest>>()

        serviceScope.launch {
            // getting category
            val category = db.collection(LAB_TEST_CATEGORY_COLLECTION)
                .whereEqualTo("title", categoryTitle)
                .limit(1)
                .get(Source.CACHE)
                .await()
                .documents
                .first()
                .reference

            // getting lab test from cache
            val testsFromCache = db.collection(LAB_TEST_COLLECTION)
                .whereEqualTo("category", category)
                .get(Source.CACHE)
                .await()
                .toLabTests(Source.CACHE)

            // assigning found value if any
            testsByCategory.postValue(testsFromCache)

            // Getting data from server
            db.collection(LAB_TEST_COLLECTION)
                .whereEqualTo("category", category)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        Timber.e("Network error: %s", error.message)
                        return@addSnapshotListener
                    }

                    serviceScope.launch {
                        snapshot?.let {
                            testsByCategory.postValue(it.toLabTests(Source.SERVER))
                        }
                    }
                }
        }

        return testsByCategory
    }

    fun getLabTestCategory(title: String): LiveData<LabTestCategory> {
        val category = MutableLiveData<LabTestCategory>()
        serviceScope.launch {
            // Get cache first
            val cache = db.collection(LAB_TEST_CATEGORY_COLLECTION)
                .whereEqualTo("title", title).get(Source.CACHE).await()
            category.postValue(cache.toObjects<LabTestCategory>().firstOrNull() ?: LabTestCategory())

            // Listen for changes
            db.collection(LAB_TEST_CATEGORY_COLLECTION)
                .whereEqualTo("title", title).addSnapshotListener{ snapshot, e ->
                    if (e != null) {
                        Timber.e("Network error: ${e.message}")
                    } else {
                        snapshot?.let { docs ->
                            val value = docs.toObjects<LabTestCategory>().firstOrNull()
                            value?.let { newValue ->
                                if (value != category.value) category.postValue(newValue)
                            }
                        }
                    }
                }
        }
        return category
    }

    fun getLabTestCategories(): LiveData<List<LabTestCategory>> {
        val list = MutableLiveData<List<LabTestCategory>>(listOf())
        serviceScope.launch {
            // Get cache first
            val cache = db.collection(LAB_TEST_CATEGORY_COLLECTION).get(Source.CACHE).await()
            list.postValue(cache.toObjects(LabTestCategory::class.java))

            // Check server data changes and update cache
            db.collection(LAB_TEST_CATEGORY_COLLECTION).addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Timber.e("Network error: ${e.message}")
                }

                snapshot?.let {
                    Timber.i("Getting new categories data from server")
                    list.postValue(snapshot.toObjects(LabTestCategory::class.java))
                }
            }
        }
        return list
    }
}