package com.gameloop.laboratorioclinicoproc.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gameloop.laboratorioclinicoproc.database.model.labtestcategory.LabTestCategory
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import timber.log.Timber

private const val LAB_TEST_CATEGORY_COLLECTION = "LabTestCategory"

class LabNetworkService {
    private val serviceScope = CoroutineScope(Dispatchers.IO)
    private val db = Firebase.firestore

    init {
        // Keep latest results cached
        db.firestoreSettings = firestoreSettings {
            isPersistenceEnabled = true
            cacheSizeBytes = FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED
        }
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