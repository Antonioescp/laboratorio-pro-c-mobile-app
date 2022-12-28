package com.gameloop.laboratorioclinicoproc.views.labtestcategoriesdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gameloop.laboratorioclinicoproc.database.model.labtestcategory.LabTestCategory

class LabCategoryDetailViewModel(labTestCategory: LabTestCategory) : ViewModel() {
    private val _labTestCategory = MutableLiveData(labTestCategory)
    val labTestCategory: LiveData<LabTestCategory> = _labTestCategory
}