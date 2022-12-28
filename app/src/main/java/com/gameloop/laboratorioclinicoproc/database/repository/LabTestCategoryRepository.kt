package com.gameloop.laboratorioclinicoproc.database.repository

import androidx.lifecycle.map
import com.gameloop.laboratorioclinicoproc.database.dao.LabTestCategoryDao
import com.gameloop.laboratorioclinicoproc.network.LabNetworkService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LabTestCategoryRepository(private val categoriesDao: LabTestCategoryDao) {
    val categories = categoriesDao.getAll()

    suspend fun refreshCategories() = withContext(Dispatchers.IO) {
        // Getting categories from server
        val newCategories = LabNetworkService().getLabTestCategories()

        // Checking if categories where deleted and deleting them from cache
        val deletedCategories = categories.value?.filter { !newCategories.contains(it) }
        deletedCategories?.let { deletedCategories.forEach { categoriesDao.delete(it.title) } }

        // Caching new data
        categoriesDao.insertAll(newCategories)
    }
}