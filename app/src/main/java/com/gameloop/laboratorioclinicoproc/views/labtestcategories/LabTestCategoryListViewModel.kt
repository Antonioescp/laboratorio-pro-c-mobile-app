package com.gameloop.laboratorioclinicoproc.views.labtestcategories

import androidx.lifecycle.ViewModel
import com.gameloop.laboratorioclinicoproc.network.LabNetworkService

class LabTestCategoryListViewModel(private val categoryDatabase: LabNetworkService) : ViewModel() {
    val categories = categoryDatabase.getLabTestCategories()
}