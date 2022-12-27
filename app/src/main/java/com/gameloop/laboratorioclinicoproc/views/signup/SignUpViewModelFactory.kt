package com.gameloop.laboratorioclinicoproc.views.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gameloop.laboratorioclinicoproc.database.LabDatabase
import com.gameloop.laboratorioclinicoproc.database.dao.PatientDao

@Suppress("UNCHECKED_CAST")
class SignUpViewModelFactory(private val patientDao: PatientDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
            return SignUpViewModel(patientDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}