package com.gameloop.laboratorioclinicoproc.views.appointments.detail.dialogs.selectpatients

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.gameloop.laboratorioclinicoproc.database.LabDatabase
import com.gameloop.laboratorioclinicoproc.database.model.patient.Patient
import com.gameloop.laboratorioclinicoproc.databinding.DialogSelectPatientBinding
import com.gameloop.laboratorioclinicoproc.views.appointments.detail.dialogs.selectpatients.adapters.SelectPatientAdapter

class SelectPatientDialog(
    private val previouslySelectedPatients: List<Patient>,
    private val listener: Listener
  ) : DialogFragment() {

    interface Listener {
        fun onClickButtonConfirm(selectedPatients: List<Patient>) {}
        fun onClickButtonCancel(selectedPatient: List<Patient>) {}
    }

    private lateinit var binding: DialogSelectPatientBinding

    private val viewModel by lazy {
        val app = requireActivity().application
        val patientsDao = LabDatabase.getInstance(app).patients
        val factory = SelectPatientViewModelFactory(patientsDao)
        ViewModelProvider(this, factory)[SelectPatientViewModel::class.java]
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater
        binding = DialogSelectPatientBinding.inflate(inflater)
        builder.setView(binding.root)

        binding.viewModel = viewModel

        setUpPatientsList()
        setUpCancelButton()
        setUpConfirmButton()

        return builder.create()
    }

    private fun setUpConfirmButton() {
        binding.btnConfirm.setOnClickListener {
            dismiss()
            listener.onClickButtonConfirm(viewModel.selectedPatients)
        }
    }

    private fun setUpCancelButton() {
        binding.btnCancel.setOnClickListener {
            dismiss()
            listener.onClickButtonCancel(viewModel.selectedPatients)
        }
    }

    private fun setUpPatientsList() {
        val adapter = SelectPatientAdapter { patient, isChecked ->
            if (isChecked) {
                if (!viewModel.selectedPatients.contains(patient)) {
                    viewModel.selectedPatients.add(patient)
                }
            } else {
                if (viewModel.selectedPatients.contains(patient)) {
                    viewModel.selectedPatients.remove(patient)
                }
            }
        }

        viewModel.patients.observe(this) {
            it?.let {
                // Getting patients not already selected
                val remainingPatients = it.filter { patient ->
                    !previouslySelectedPatients.contains(patient)
                }
                adapter.submitList(remainingPatients)
            }
        }

        binding.rvPatients.adapter = adapter
    }
}