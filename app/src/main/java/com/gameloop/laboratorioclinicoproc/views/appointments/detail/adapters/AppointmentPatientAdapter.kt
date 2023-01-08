package com.gameloop.laboratorioclinicoproc.views.appointments.detail.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gameloop.laboratorioclinicoproc.database.model.labtest.LabTest
import com.gameloop.laboratorioclinicoproc.database.model.patient.Patient
import com.gameloop.laboratorioclinicoproc.databinding.ListItemAppointmentPatientBinding

class AppointmentPatientAdapter(
    private val listener: Listener,
) : ListAdapter<Pair<Patient, List<LabTest>>, AppointmentPatientAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder private constructor(private val binding: ListItemAppointmentPatientBinding)
        : RecyclerView.ViewHolder(binding.root) {

            fun bind(
                patientAppointments: Pair<Patient, List<LabTest>>,
                listener: Listener
            ) {
                binding.patient = patientAppointments.first

                binding.ivDelete.setOnClickListener { listener.onDelete(patientAppointments.first) }
                binding.btnAddTest
                    .setOnClickListener { listener.onAddLabTest(patientAppointments.first) }

                val adapter = AppointmentLabTestAdapter(object: AppointmentLabTestAdapter.Listener {
                    override fun onSee(labTest: LabTest) {
                        listener.onSeeLabTest(labTest)
                    }

                    override fun onDelete(labTest: LabTest) {
                        listener.onDeleteLabTest(patientAppointments.first, labTest)
                    }
                })

                binding.rvTests.adapter = adapter
                adapter.submitList(patientAppointments.second)
            }

            companion object {
                fun from(parent: ViewGroup): ViewHolder {
                    val inflater = LayoutInflater.from(parent.context)
                    val binding = ListItemAppointmentPatientBinding.inflate(
                        inflater,
                        parent,
                        false
                    )
                    return ViewHolder(binding)
                }
            }

        }

    class DiffCallback : DiffUtil.ItemCallback<Pair<Patient, List<LabTest>>>() {
        override fun areItemsTheSame(
            oldItem: Pair<Patient, List<LabTest>>,
            newItem: Pair<Patient, List<LabTest>>
        ): Boolean {
            val (oldPatient, oldLabTests) = oldItem
            val (newPatient, newLabTests) = newItem

            return oldPatient.id == newPatient.id && oldLabTests == newLabTests
        }

        override fun areContentsTheSame(
            oldItem: Pair<Patient, List<LabTest>>,
            newItem: Pair<Patient, List<LabTest>>
        ): Boolean {
            val (oldPatient, oldLabTests) = oldItem
            val (newPatient, newLabTests) = newItem

            return oldPatient == newPatient && oldLabTests.containsAll(newLabTests)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
        = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
        = holder.bind(getItem(position), listener)

    interface Listener {
        fun onDelete(patient: Patient) {}
        fun onAddLabTest(patient: Patient) {}
        fun onDeleteLabTest(patient: Patient, labTest: LabTest) {}
        fun onSeeLabTest(labTest: LabTest) {}
    }
}