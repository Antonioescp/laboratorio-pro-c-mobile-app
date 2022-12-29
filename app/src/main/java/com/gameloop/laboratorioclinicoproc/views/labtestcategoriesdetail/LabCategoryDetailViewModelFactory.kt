package com.gameloop.laboratorioclinicoproc.views.labtestcategoriesdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class LabCategoryDetailViewModelFactory(private val categoryTitle: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LabCategoryDetailViewModel::class.java)) {
            return LabCategoryDetailViewModel(categoryTitle) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}