package com.gameloop.laboratorioclinicoproc.views.appointments.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.gameloop.laboratorioclinicoproc.database.model.appointment.Appointment
import com.gameloop.laboratorioclinicoproc.database.model.labtest.LabTest
import com.gameloop.laboratorioclinicoproc.database.model.patient.Patient

class AppointmentDetailViewModel : ViewModel() {
    private var _arePatientsHidden = MutableLiveData(true)
    val arePatientsHidden: LiveData<Boolean>
        get() = _arePatientsHidden

    private var _appointment = Appointment()

    val selectedPatients: Set<Patient>
        get() = _appointment.tests.keys

    private var _patientAlreadyAdded = MutableLiveData(false)
    val patientAlreadyAdded: LiveData<Boolean> = _patientAlreadyAdded

    val selectedPatientsAppointments
        get() = selectedPatients.map { patient ->
            val patientTests = _appointment.tests[patient]!!
            Pair(patient, patientTests.map { it.first })
        }

    // Patients insertion events
    private var _eventPatientAdded = MutableLiveData<Boolean>()
    val eventPatientAdded: LiveData<Boolean> = _eventPatientAdded

    private var _eventPatientRemoved = MutableLiveData<Boolean>()
    val eventPatientRemoved: LiveData<Boolean> = _eventPatientRemoved

    private var _eventPatientListChange = MutableLiveData<Boolean>()
    val eventPatientListChange: LiveData<Boolean> = _eventPatientListChange

    fun onPatientListChangeCompleted() {
        _eventPatientListChange.value = false
    }

    fun onPatientAlreadyAddedComplete() {
        _patientAlreadyAdded.value = null
    }

    fun togglePatients() {
        _arePatientsHidden.value = _arePatientsHidden.value != true
    }

    fun addPatient(patient: Patient) {
        if (_appointment.tests.containsKey(patient)) {
            _patientAlreadyAdded.value = true
        } else {
            _appointment.tests[patient] = mutableListOf()
            _eventPatientListChange.value = true
            _patientAlreadyAdded.value = false
        }
    }

    fun removePatient(patient: Patient) {
        if (_appointment.tests.containsKey(patient)) {
            _appointment.tests.remove(patient)
            _eventPatientListChange.value = true
        }
    }
}