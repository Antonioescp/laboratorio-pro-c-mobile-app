package com.gameloop.laboratorioclinicoproc.database.model.appointment

import com.gameloop.laboratorioclinicoproc.database.model.labtest.LabTest
import com.gameloop.laboratorioclinicoproc.database.model.patient.Patient
import java.util.*


data class AppointmentNetwork(
    // For every patient you can have any amount of lab tests, the string will be an url to an image
    var tests: MutableMap<String, MutableList<Pair<LabTest, String>>> = mutableMapOf(),
    var date: Date = Date(),
    var description: String = "",
    var state: Appointment.State = Appointment.State.Pending,
) : java.io.Serializable {

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