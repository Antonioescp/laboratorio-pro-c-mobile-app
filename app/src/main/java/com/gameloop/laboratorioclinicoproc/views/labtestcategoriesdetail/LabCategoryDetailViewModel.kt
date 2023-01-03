package com.gameloop.laboratorioclinicoproc.views.labtestcategoriesdetail

import androidx.lifecycle.ViewModel
import com.gameloop.laboratorioclinicoproc.network.LabNetworkService

class LabCategoryDetailViewModel(categoryTitle: String) : ViewModel() {
    val currentCategory = LabNetworkService.instance.getLabTestCategory(categoryTitle)
}