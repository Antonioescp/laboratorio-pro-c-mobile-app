package com.gameloop.laboratorioclinicoproc.database.model.labtest

import androidx.annotation.Keep
import com.gameloop.laboratorioclinicoproc.database.model.labtestcategory.LabTestCategory

data class LabTest(
    var title: String = "",
    var price: Double = 0.0,
    var category: LabTestCategory = LabTestCategory(),
    var recommendations: List<String> = listOf()
) : java.io.Serializable