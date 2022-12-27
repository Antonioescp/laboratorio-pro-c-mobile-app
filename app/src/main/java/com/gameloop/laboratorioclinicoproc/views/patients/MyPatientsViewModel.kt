package com.gameloop.laboratorioclinicoproc.views.patients

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gameloop.laboratorioclinicoproc.database.dao.PatientDao
import com.gameloop.laboratorioclinicoproc.database.model.patient.Patient
import kotlinx.coroutines.launch

class MyPatientsViewModel(private val patientsDao: PatientDao) : ViewModel() {
    val patients = patientsDao.getAll()

    fun deletePatient(patient: Patient) {
        viewModelScope.launch {
            patientsDao.delete(patient.id)
        }
    }
}