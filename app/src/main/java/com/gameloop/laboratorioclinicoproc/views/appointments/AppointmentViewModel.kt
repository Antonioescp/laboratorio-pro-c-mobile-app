package com.gameloop.laboratorioclinicoproc.views.appointments

import androidx.lifecycle.ViewModel
import com.gameloop.laboratorioclinicoproc.network.LabNetworkService

class AppointmentViewModel : ViewModel() {
    val appointments = LabNetworkService.instance.getAppointments()
}