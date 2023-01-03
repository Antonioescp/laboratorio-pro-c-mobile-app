package com.gameloop.laboratorioclinicoproc.views.labtest

import androidx.lifecycle.ViewModel
import com.gameloop.laboratorioclinicoproc.network.LabNetworkService

class LabTestViewModel(labTestCategoryTitle: String) : ViewModel() {
    val availableTests = LabNetworkService.instance.getLabTestsByCategory(labTestCategoryTitle)
}