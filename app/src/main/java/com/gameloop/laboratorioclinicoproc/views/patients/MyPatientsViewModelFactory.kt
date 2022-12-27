package com.gameloop.laboratorioclinicoproc.views.patients

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gameloop.laboratorioclinicoproc.database.dao.PatientDao

@Suppress("UNCHECKED_CAST")
class MyPatientsViewModelFactory(private val patientsDao: PatientDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyPatientsViewModel::class.java)) {
            return MyPatientsViewModel(patientsDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}