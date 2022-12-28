package com.gameloop.laboratorioclinicoproc.database.model.labtestcategory

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lab_test_category")
data class LabTestCategory(
    @PrimaryKey
    var title: String = "",

    @ColumnInfo
    var imgSrc: String = "",

    @ColumnInfo
    var recommendations: List<String> = listOf()
) : java.io.Serializable
