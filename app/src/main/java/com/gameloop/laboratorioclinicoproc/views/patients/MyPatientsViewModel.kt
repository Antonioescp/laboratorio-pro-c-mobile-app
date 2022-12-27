package com.gameloop.laboratorioclinicoproc.views.patients

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gameloop.laboratorioclinicoproc.database.dao.PatientDao
import com.gameloop.laboratorioclinicoproc.database.model.patient.Patient
import kotlinx.coroutines.launch

class MyPatientsViewModel(private val patientsDao: PatientDao) : ViewModel() {
    val patients = patientsDao.getAll()

    private val _eventNavigateToAddPatient = MutableLiveData<Boolean>()
    val eventNavigateToAddPatient: LiveData<Boolean> = _eventNavigateToAddPatient

    fun onNavigateToAddPatient() {
        _eventNavigateToAddPatient.value = true
    }

    fun onNavigateToAddPatientComplete() {
        _eventNavigateToAddPatient.value = false
    }

    fun deletePatient(patient: Patient) {
        viewModelScope.launch {
            patientsDao.delete(patient.id)
        }
    }
}