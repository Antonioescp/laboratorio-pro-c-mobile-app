package com.gameloop.laboratorioclinicoproc.views.appointments.detail.dialogs.selectpatients.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gameloop.laboratorioclinicoproc.database.model.patient.Patient
import com.gameloop.laboratorioclinicoproc.databinding.ListItemSelectPatientBinding

class SelectPatientAdapter(private val onSelect: (patient: Patient, selected: Boolean) -> Unit)
    : ListAdapter<Patient, SelectPatientAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder private constructor(private val binding: ListItemSelectPatientBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(patient: Patient, onSelect: (patient: Patient, selected: Boolean) -> Unit) {
            binding.patient = patient

            // Changing state
            binding.root.setOnClickListener {
                binding.cbSelected.isChecked = !binding.cbSelected.isChecked
            }

            // Updating patient status
            binding.cbSelected.setOnCheckedChangeListener { _, isChecked ->
                onSelect(patient, isChecked)
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ListItemSelectPatientBinding.inflate(
                    inflater,
                    parent,
                    false
                )
                return ViewHolder(binding)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Patient>() {
        override fun areItemsTheSame(oldItem: Patient, newItem: Patient): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Patient, newItem: Patient): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
        = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
        = holder.bind(getItem(position), onSelect)
}