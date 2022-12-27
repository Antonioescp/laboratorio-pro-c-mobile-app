package com.gameloop.laboratorioclinicoproc.database.model.patient

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "patients")
data class Patient(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "full_name")
    var fullName: String = "",

    @ColumnInfo
    var age: Int = 1,

    @ColumnInfo
    var email: String = "",

    @ColumnInfo()
    var sex: Sex? = Sex.MALE,

    @ColumnInfo(name = "chronic_diseases")
    var chronicDiseases: String = ""
) : java.io.Serializable