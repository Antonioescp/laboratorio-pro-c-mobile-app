package com.gameloop.laboratorioclinicoproc.views.patients

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gameloop.laboratorioclinicoproc.database.dao.PatientDao
import com.gameloop.laboratorioclinicoproc.database.model.patient.Patient
import kotlinx.coroutines.launch

open class MyPatientsViewModel(private val patientsDao: PatientDao) : ViewModel() {
    val patients = patientsDao.getAll()

    val noPatients = Transformations.map(patients) {
        it?.let {
            it.isEmpty()
        }
    }

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