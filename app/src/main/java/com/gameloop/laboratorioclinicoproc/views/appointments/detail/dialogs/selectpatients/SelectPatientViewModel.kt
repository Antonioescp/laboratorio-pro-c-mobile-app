package com.gameloop.laboratorioclinicoproc.views.appointments.detail.dialogs.selectpatients

import com.gameloop.laboratorioclinicoproc.database.dao.PatientDao
import com.gameloop.laboratorioclinicoproc.database.model.patient.Patient
import com.gameloop.laboratorioclinicoproc.views.patients.MyPatientsViewModel

class SelectPatientViewModel(patientsDao: PatientDao) : MyPatientsViewModel(patientsDao) {
    val selectedPatients = mutableListOf<Patient>()
}