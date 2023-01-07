package com.gameloop.laboratorioclinicoproc.views.appointments

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gameloop.laboratorioclinicoproc.R
import com.gameloop.laboratorioclinicoproc.databinding.FragmentAppointmentsBinding

class AppointmentsFragment : Fragment(), MenuProvider {

    private lateinit var binding: FragmentAppointmentsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAppointmentsBinding.inflate(inflater)
        return binding.root
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