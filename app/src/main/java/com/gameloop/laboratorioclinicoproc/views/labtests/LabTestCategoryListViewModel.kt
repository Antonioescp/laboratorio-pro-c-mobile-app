package com.gameloop.laboratorioclinicoproc.views.labtests

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gameloop.laboratorioclinicoproc.database.LabDatabase
import com.gameloop.laboratorioclinicoproc.database.repository.LabTestCategoryRepository
import kotlinx.coroutines.launch

class LabTestCategoryListViewModel(private val database: LabDatabase) : ViewModel() {
    private val labTestCategoryRepository = LabTestCategoryRepository(database.labTestCategories)
    val categories = labTestCategoryRepository.categories

    init {
        viewModelScope.launch {
            labTestCategoryRepository.refreshCategories()
        }
    }
}