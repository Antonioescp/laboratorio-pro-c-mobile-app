package com.gameloop.laboratorioclinicoproc.database.model.appointment

import com.gameloop.laboratorioclinicoproc.database.model.labtest.LabTest
import com.gameloop.laboratorioclinicoproc.database.model.patient.Patient
import java.util.Date

data class Appointment(
    // For every patient you can have any amount of lab tests, the string will be an url to an image
    var tests: MutableMap<Patient, MutableList<Pair<LabTest, String>>> = mutableMapOf(),
    var date: Date = Date(),
    var description: String = "",
    var state: State = State.Pending,
) : java.io.Serializable {
    enum class State {
        Pending,
        Cancelled,
        InProgress,
        Completed
    }

    val totalPrice: Double
        get() {
            var total = 0.0
            tests.forEach { (patient, appointments) ->
                appointments.forEach { (test, _) ->
                    total += test.price
                }
            }
            return total
        }
}