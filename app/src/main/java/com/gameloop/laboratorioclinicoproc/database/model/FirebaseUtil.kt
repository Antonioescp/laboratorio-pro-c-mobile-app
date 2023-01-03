package com.gameloop.laboratorioclinicoproc.database.model

import com.gameloop.laboratorioclinicoproc.database.model.labtest.LabTest
import com.gameloop.laboratorioclinicoproc.database.model.labtestcategory.LabTestCategory
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

inline fun<reified T> QueryDocumentSnapshot.get(field: String): T? {
    return get(field, T::class.java)
}

inline fun<reified T> DocumentSnapshot.get(field: String): T? {
    return get(field, T::class.java)
}

suspend fun QuerySnapshot.toLabTests(source: Source): List<LabTest> = withContext(Dispatchers.IO) {
    val items = mutableListOf<LabTest>()
    forEach { doc ->
        // Mapping to LabTest
        doc?.let {
            val title = doc.get<String>("title")
            val price = doc.get<Double>("price")
            val recommendations = doc.get("recommendations")?.let { it as List<String> } ?: listOf()
            val category = doc.get<DocumentReference>("category")
            var categoryObj: LabTestCategory? = null

            category?.let {
                categoryObj = it.get(source).await().toObject()
            }

            val newItem = LabTest(
                title ?: "",
                price ?: 0.0,
                categoryObj ?: LabTestCategory(),
                recommendations
            )

            items.add(newItem)
        }
    }
    return@withContext items
}