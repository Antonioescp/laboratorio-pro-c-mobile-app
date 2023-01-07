package com.gameloop.laboratorioclinicoproc.views.appointments.detail.dialogs.selectpatients

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gameloop.laboratorioclinicoproc.database.dao.PatientDao

@Suppress("UNCHECKED_CAST")
class SelectPatientViewModelFactory(private val patientsDao: PatientDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SelectPatientViewModel::class.java)) {
            return SelectPatientViewModel(patientsDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}