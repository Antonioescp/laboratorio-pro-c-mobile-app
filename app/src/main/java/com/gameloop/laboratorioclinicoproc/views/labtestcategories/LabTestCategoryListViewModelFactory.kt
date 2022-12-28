package com.gameloop.laboratorioclinicoproc.views.labtestcategories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gameloop.laboratorioclinicoproc.database.LabDatabase
import com.gameloop.laboratorioclinicoproc.network.LabNetworkService

@Suppress("UNCHECKED_CAST")
class LabTestCategoryListViewModelFactory(private val categoriesDatabase: LabNetworkService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LabTestCategoryListViewModel::class.java)){
            return LabTestCategoryListViewModel(categoriesDatabase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}