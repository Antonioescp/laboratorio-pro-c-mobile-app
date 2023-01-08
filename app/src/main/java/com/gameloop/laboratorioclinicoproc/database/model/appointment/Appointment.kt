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

    fun toNetworkModel(): AppointmentNetwork {
        val newMap = tests.map { (patient, tests) ->
            return@map Pair(patient.fullName, tests)
        }

        val appointmentNetwork = AppointmentNetwork(
            date = date,
            description = description,
            state = state
        )

        newMap.forEach { (name, tests) ->
            val newTests = mutableListOf<DefaultPair<LabTest, String>>()

            tests.forEach { (first, second) ->
                newTests.add(DefaultPair(first, second))
            }

            appointmentNetwork.tests[name] = newTests
        }

        return appointmentNetwork
    }
}