package com.gameloop.laboratorioclinicoproc.views.appointments.detail

import androidx.lifecycle.*
import com.gameloop.laboratorioclinicoproc.database.model.appointment.Appointment
import com.gameloop.laboratorioclinicoproc.database.model.labtest.LabTest
import com.gameloop.laboratorioclinicoproc.database.model.patient.Patient
import com.gameloop.laboratorioclinicoproc.network.LabNetworkService
import kotlinx.coroutines.launch
import java.util.*

class AppointmentDetailViewModel : ViewModel() {

    val labCategories = LabNetworkService.instance.getLabTestCategories()

    private var _isAppointmentBeingCreated = MutableLiveData(false)
    val isAppointmentBeingCreated: LiveData<Boolean> = _isAppointmentBeingCreated

    private var _arePatientsHidden = MutableLiveData(true)
    val arePatientsHidden: LiveData<Boolean>
        get() = _arePatientsHidden

    private var _appointment = Appointment()
    val totalPrice
        get() = _appointment.totalPrice

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

    private var _eventNavigateBack = MutableLiveData<Boolean>()
    val eventNavigateBack: LiveData<Boolean> = _eventNavigateBack

    private var _eventAppointmentAdded = MutableLiveData<Boolean?>()
    val eventAppointmentAdded: LiveData<Boolean?> = _eventAppointmentAdded

    fun onAppointmentAddedCompleted() {
        _eventAppointmentAdded.value = null
    }

    fun onNavigateBackCompleted() {
        _eventNavigateBack.value = false
    }

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

    fun getLabTestsFor(patient: Patient): List<LabTest> {
        if (_appointment.tests.containsKey(patient)) {
            val patientAppointments = _appointment.tests[patient]!!
            return patientAppointments.map { it.first }
        }
        throw IllegalArgumentException("$patient not in appointments")
    }

    fun addLabTestFor(patient: Patient, labTest: LabTest) {
        if (_appointment.tests.containsKey(patient)) {
            val patientTests = _appointment.tests[patient]!!
            if (patientTests.map { it.first }.contains(labTest)) {
                throw IllegalArgumentException("$labTest already set for $patient")
            } else {
                patientTests.add(Pair(labTest, ""))
                _eventPatientListChange.value = true
            }
        } else {
            throw IllegalArgumentException("$patient not in appointments")
        }
    }

    fun removeLabTestFor(patient: Patient, labTest: LabTest) {
        if (_appointment.tests.containsKey(patient)) {
            val tests = _appointment.tests[patient]!!
            if (tests.map { it.first }.contains(labTest)) {
                tests.remove(Pair(labTest, ""))
                _eventPatientListChange.value = true
            } else {
                throw IllegalArgumentException("$labTest not in patient appointment")
            }
        } else {
            throw IllegalArgumentException("$patient not in map")
        }
    }

    // Adding appointment
    private val _eventAddAppointment = MutableLiveData(false)
    val eventAddAppointment: LiveData<Boolean> = _eventAddAppointment

    fun onAddAppointment() {
        _eventAddAppointment.value = true
    }

    fun onAddAppointmentCompleted() {
        _eventAddAppointment.value = null
    }

    fun addAppointment() {
        viewModelScope.launch {
            _isAppointmentBeingCreated.value = true
            LabNetworkService.instance.addAppointment(_appointment)
            _isAppointmentBeingCreated.value = false
            _eventNavigateBack.value = true
            _eventAppointmentAdded.value = true
        }
    }

    val date: Date
        get() {
            return _appointment.date
        }

    fun setDate(year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        _appointment.date = calendar.time
    }

    fun setDescription(description: String) {
        _appointment.description = description
    }
}