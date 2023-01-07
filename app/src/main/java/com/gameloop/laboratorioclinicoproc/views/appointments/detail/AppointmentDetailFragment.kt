package com.gameloop.laboratorioclinicoproc.views.appointments.detail

import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.gameloop.laboratorioclinicoproc.R
import com.gameloop.laboratorioclinicoproc.database.model.labtest.LabTest
import com.gameloop.laboratorioclinicoproc.database.model.patient.Patient
import com.gameloop.laboratorioclinicoproc.databinding.FragmentAppointmentDetailBinding
import com.gameloop.laboratorioclinicoproc.views.appointments.detail.adapters.AppointmentPatientAdapter
import com.gameloop.laboratorioclinicoproc.views.appointments.detail.dialogs.selectpatients.SelectPatientDialog

class AppointmentDetailFragment : Fragment() {
    private lateinit var binding: FragmentAppointmentDetailBinding

    private val args by navArgs<AppointmentDetailFragmentArgs>()

    private val viewModel by lazy {
        ViewModelProvider(this)[AppointmentDetailViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAppointmentDetailBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        setUpPatientsDropDownAnimation()
        setUpPatientAddButton()
        setUpPatientsList()

        viewModel.patientAlreadyAdded.observe(viewLifecycleOwner) { patientAlreadyInMap ->
            patientAlreadyInMap?.let {
                if (patientAlreadyInMap) {
                    Toast.makeText(
                        requireContext(),
                        "The patient you are trying to add is already in map",
                        Toast.LENGTH_SHORT
                    ).show()
                    viewModel.onPatientAlreadyAddedComplete()
                }
            }
        }

        return binding.root
    }

    private fun setUpPatientsList() {
        val adapter = AppointmentPatientAdapter(object: AppointmentPatientAdapter.Listener {
            override fun onDelete(patient: Patient) {
                viewModel.removePatient(patient)
            }

            override fun onAddLabTest(labTests: List<LabTest>) {
                Toast.makeText(
                    requireContext(),
                    "Trying to add labtest",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        viewModel.eventPatientListChange.observe(viewLifecycleOwner) { hasChanged ->
            hasChanged?.let {
                if (hasChanged) {
                    TransitionManager.beginDelayedTransition(
                        binding.cvAppointments,
                        AutoTransition()
                    )
                    adapter.submitList(viewModel.selectedPatientsAppointments)
                    viewModel.onPatientListChangeCompleted()
                }
            }
        }

        binding.rvPatients.adapter = adapter
    }

    private fun setUpPatientsDropDownAnimation() {
        viewModel.arePatientsHidden.observe(viewLifecycleOwner) { areHidden ->
            areHidden?.let {
                TransitionManager.beginDelayedTransition(
                    binding.cvAppointments,
                    AutoTransition()
                )

                var drawable = ContextCompat.getDrawable(
                    requireContext(),
                    when (areHidden) {
                        true -> R.drawable.ic_baseline_keyboard_arrow_right_24
                        else -> R.drawable.ic_baseline_keyboard_arrow_down_24
                    }
                )

                binding.tvPatients.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    null, null, drawable, null
                )
            }
        }
    }

    private fun setUpPatientAddButton() {
        binding.btnAddPatient.setOnClickListener {
            val fragManager = parentFragmentManager

            // Adding patients to existing patients
            val selectPatientDialog = SelectPatientDialog(
                viewModel.selectedPatients.toList(),
                object : SelectPatientDialog.Listener {
                    override fun onClickButtonConfirm(selectedPatients: List<Patient>) {
                        selectedPatients.forEach {
                            viewModel.addPatient(it)
                        }
                    }
                }
            )

            selectPatientDialog.show(fragManager, "select_patient_dialog")
        }
    }
}