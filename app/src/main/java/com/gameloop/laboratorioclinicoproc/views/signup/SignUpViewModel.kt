package com.gameloop.laboratorioclinicoproc.views.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gameloop.laboratorioclinicoproc.database.LabDatabase
import com.gameloop.laboratorioclinicoproc.database.dao.PatientDao
import com.gameloop.laboratorioclinicoproc.database.model.patient.Patient
import kotlinx.coroutines.launch
import timber.log.Timber

class SignUpViewModel(private val patientDao: PatientDao) : ViewModel() {
    private val _patient = MutableLiveData(Patient())
    val patient: LiveData<Patient> = _patient

    private val _eventSignUp = MutableLiveData<Boolean>()
    val eventSignUp: LiveData<Boolean> = _eventSignUp

    fun insertPatient() {
        viewModelScope.launch {
            patient.value?.let {
                patientDao.insert(it)
            }
        }
    }

    fun onSignUp() {
        _eventSignUp.value = true
    }

    fun onSignUpComplete() {
        _eventSignUp.value = false
    }
}