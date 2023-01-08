package com.gameloop.laboratorioclinicoproc.database.model.appointment

import com.gameloop.laboratorioclinicoproc.database.model.labtest.LabTest
import java.util.*

data class DefaultPair<F, S>(
    var first: F? = null,
    var second: S? = null
) : java.io.Serializable

data class AppointmentNetwork(
    // For every patient you can have any amount of lab tests, the string will be an url to an image
    var tests: MutableMap<String, MutableList<DefaultPair<LabTest, String>>> = mutableMapOf(),
    var date: Date = Date(),
    var description: String = "",
    var state: Appointment.State = Appointment.State.Pending,
) : java.io.Serializable {

    val totalPrice: Double
        get() {
            var total = 0.0
            tests.forEach { (patient, appointments) ->
                appointments.forEach { (test, _) ->
                    test?.let {
                        total += test.price
                    }
                }
            }
            return total
        }

    val isCancellable: Boolean
        get() {
            return state == Appointment.State.Pending
        }

    val isCompleted: Boolean
        get() {
            return state == Appointment.State.Completed
        }
}