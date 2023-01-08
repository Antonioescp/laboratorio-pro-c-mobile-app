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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.gameloop.laboratorioclinicoproc.R
import com.gameloop.laboratorioclinicoproc.database.model.labtest.LabTest
import com.gameloop.laboratorioclinicoproc.database.model.patient.Patient
import com.gameloop.laboratorioclinicoproc.databinding.FragmentAppointmentDetailBinding
import com.gameloop.laboratorioclinicoproc.setPrice
import com.gameloop.laboratorioclinicoproc.validateEmpty
import com.gameloop.laboratorioclinicoproc.views.appointments.detail.adapters.AppointmentPatientAdapter
import com.gameloop.laboratorioclinicoproc.views.appointments.detail.dialogs.SelectDateDialog
import com.gameloop.laboratorioclinicoproc.views.appointments.detail.dialogs.selectlabtest.SelectLabTestDialog
import com.gameloop.laboratorioclinicoproc.views.appointments.detail.dialogs.selectpatients.SelectPatientDialog
import java.text.DateFormat
import java.time.LocalDate
import java.util.*

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
        setUpDatePicker()

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

        viewModel.eventNavigateBack.observe(viewLifecycleOwner) { shouldGoBack ->
            shouldGoBack?.let {
                if (shouldGoBack) {
                    findNavController().navigateUp()
                    viewModel.onNavigateBackCompleted()
                }
            }
        }

        viewModel.eventAppointmentAdded.observe(viewLifecycleOwner) { wasAdded ->
            wasAdded?.let {
                if (wasAdded) {
                    Toast.makeText(
                        requireActivity(),
                        "Cita agregada con éxito",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        viewModel.eventAddAppointment.observe(viewLifecycleOwner) { shouldAdd ->
            shouldAdd?.let {
                if (shouldAdd && verifyFields()) {
                    viewModel.addAppointment()
                    viewModel.onAddAppointmentCompleted()
                }
            }
        }

        return binding.root
    }

    private fun setUpDatePicker() {
        binding.btnCalendar.setOnClickListener {
            SelectDateDialog { _, year, month, dayOfMonth ->
                viewModel.setDate(year, month, dayOfMonth)

                val formattedDate = DateFormat
                    .getDateInstance(DateFormat.FULL, Locale.forLanguageTag("es"))
                    .format(viewModel.date)

                val dateString = "Fecha: $formattedDate"
                binding.tvDate.text = dateString
            }.show(parentFragmentManager, "select_date")
        }
    }

    private fun verifyFields(): Boolean {
        if (viewModel.totalPrice == 0.0) {
            Toast.makeText(
                requireActivity(),
                "No se ha seleccionado ningún exámen",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }

        if (binding.tvDate.text.isEmpty()) {
            Toast.makeText(
                requireContext(),
                "Por favor, elige una fecha",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }

        return binding.tietDescription.validateEmpty("No puede estar vacío")
    }

    private fun setUpPatientsList() {
        val adapter = AppointmentPatientAdapter(
            object: AppointmentPatientAdapter.Listener {
                override fun onDelete(patient: Patient) {
                    viewModel.removePatient(patient)
                }

                override fun onSeeLabTest(labTest: LabTest) {
                    val action = AppointmentDetailFragmentDirections
                        .actionAppointmentDetailFragmentToLabTestDetailFragment(labTest)
                    findNavController().navigate(action)
                }

                override fun onDeleteLabTest(patient: Patient, labTest: LabTest) {
                    viewModel.removeLabTestFor(patient, labTest)
                }

                override fun onAddLabTest(patient: Patient) {
                    val fm = parentFragmentManager
                    val selectLabDialog = SelectLabTestDialog(
                        viewModel.getLabTestsFor(patient),
                        viewModel.labCategories,
                        object: SelectLabTestDialog.Listener {
                            override fun onConfirm(labTests: List<LabTest>) {
                                labTests.forEach { labTest -> viewModel.addLabTestFor(patient, labTest) }
                            }
                        }
                    )
                    selectLabDialog.show(fm, "select_lab_test")
                }
            }
        )

        viewModel.eventPatientListChange.observe(viewLifecycleOwner) { hasChanged ->
            hasChanged?.let {
                if (hasChanged) {
                    TransitionManager.beginDelayedTransition(
                        binding.cvAppointments,
                        AutoTransition()
                    )
                    adapter.submitList(viewModel.selectedPatientsAppointments)
                    binding.tvTotal.setPrice(viewModel.totalPrice)
                    viewModel.onPatientListChangeCompleted()
                }
            }
        }

        binding.tvTotal.setPrice(viewModel.totalPrice)
        adapter.submitList(viewModel.selectedPatientsAppointments)
        binding.rvPatients.adapter = adapter
    }

    private fun setUpPatientsDropDownAnimation() {
        viewModel.arePatientsHidden.observe(viewLifecycleOwner) { areHidden ->
            areHidden?.let {
                TransitionManager.beginDelayedTransition(
                    binding.cvAppointments,
                    AutoTransition()
                )

                val drawable = ContextCompat.getDrawable(
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