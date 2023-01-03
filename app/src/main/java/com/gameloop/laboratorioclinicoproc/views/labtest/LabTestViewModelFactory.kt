package com.gameloop.laboratorioclinicoproc.views.labtest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class LabTestViewModelFactory(private val labTestCategoryTitle: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LabTestViewModel::class.java)) {
            return LabTestViewModel(labTestCategoryTitle) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}