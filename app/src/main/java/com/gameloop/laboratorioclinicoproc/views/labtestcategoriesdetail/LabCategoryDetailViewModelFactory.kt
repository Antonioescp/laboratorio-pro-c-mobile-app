package com.gameloop.laboratorioclinicoproc.views.labtestcategoriesdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gameloop.laboratorioclinicoproc.database.model.labtestcategory.LabTestCategory

@Suppress("UNCHECKED_CAST")
class LabCategoryDetailViewModelFactory(private val labCategory: LabTestCategory)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LabCategoryDetailViewModel::class.java)) {
            return LabCategoryDetailViewModel(labCategory) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}