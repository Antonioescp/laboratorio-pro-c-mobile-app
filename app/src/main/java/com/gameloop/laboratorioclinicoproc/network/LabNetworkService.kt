package com.gameloop.laboratorioclinicoproc.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gameloop.laboratorioclinicoproc.database.model.labtestcategory.LabTestCategory
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

private const val LAB_TEST_CATEGORY_COLLECTION = "LabTestCategory"

class LabNetworkService {
    private val db = Firebase.firestore

    suspend fun getLabTestCategories(): List<LabTestCategory> {
        val collection = db.collection(LAB_TEST_CATEGORY_COLLECTION).get().await()
        return collection.map { doc ->
            doc.toObject(LabTestCategory::class.java)
        }
    }
}