package com.gameloop.laboratorioclinicoproc.views.appointments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.gameloop.laboratorioclinicoproc.R
import com.gameloop.laboratorioclinicoproc.database.model.appointment.Appointment
import com.gameloop.laboratorioclinicoproc.database.model.appointment.AppointmentNetwork
import com.gameloop.laboratorioclinicoproc.databinding.FragmentAppointmentsBinding

class AppointmentsFragment : Fragment(), MenuProvider {

    private lateinit var binding: FragmentAppointmentsBinding

    private val viewModel by lazy {
        ViewModelProvider(this)[AppointmentViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAppointmentsBinding.inflate(inflater)

        setUpAppointmentsList()

        return binding.root
    }

    private fun setUpAppointmentsList() {
        val adapter = AppointmentAdapter(object: AppointmentAdapter.Listener {
            override fun onClick(view: View) {
                Toast.makeText(
                    requireContext(),
                    "appointment clicked",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onAction(appointmentNetwork: AppointmentNetwork) {
                if (appointmentNetwork.isCancellable) {
                    appointmentNetwork.state = Appointment.State.Cancelled
                } else if (appointmentNetwork.isCompleted) {
                    Toast.makeText(
                        requireContext(),
                        "should go to check results",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })

        binding.rvAppointments.adapter = adapter

        viewModel.appointments.observe(viewLifecycleOwner) {
            it?.let {
                adapter.submitList(it)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        requireActivity().addMenuProvider(this)
    }

    override fun onStop() {
        super.onStop()
        requireActivity().removeMenuProvider(this)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_appointments, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.miAddAppointment -> {
                val action = AppointmentsFragmentDirections
                    .actionAppointmentsFragmentToAppointmentDetailFragment()
                findNavController().navigate(action)
                true
            }
            else -> false
        }
    }
}