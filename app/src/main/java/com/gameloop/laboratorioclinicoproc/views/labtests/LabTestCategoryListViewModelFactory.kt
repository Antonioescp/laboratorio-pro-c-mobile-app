package com.gameloop.laboratorioclinicoproc.views.labtests

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gameloop.laboratorioclinicoproc.database.LabDatabase

@Suppress("UNCHECKED_CAST")
class LabTestCategoryListViewModelFactory(private val database: LabDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LabTestCategoryListViewModel::class.java)){
            return LabTestCategoryListViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}