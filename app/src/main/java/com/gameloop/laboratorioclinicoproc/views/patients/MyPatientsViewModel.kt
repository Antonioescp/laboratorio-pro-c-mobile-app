package com.gameloop.laboratorioclinicoproc.views.patients

import androidx.lifecycle.ViewModel
import com.gameloop.laboratorioclinicoproc.database.dao.PatientDao

class MyPatientsViewModel(patientsDao: PatientDao) : ViewModel() {
    val patients = patientsDao.getAll()
}