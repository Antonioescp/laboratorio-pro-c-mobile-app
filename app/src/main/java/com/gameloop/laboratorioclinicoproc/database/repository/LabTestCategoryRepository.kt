package com.gameloop.laboratorioclinicoproc.database.repository

import com.gameloop.laboratorioclinicoproc.database.dao.LabTestCategoryDao
import com.gameloop.laboratorioclinicoproc.network.LabNetworkService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LabTestCategoryRepository(private val categoriesDao: LabTestCategoryDao) {
    val categories = categoriesDao.getAll()

    suspend fun refreshCategories() = withContext(Dispatchers.IO) {
        val newCategories = LabNetworkService().getLabTestCategories()
        categoriesDao.insertAll(newCategories)
    }
}