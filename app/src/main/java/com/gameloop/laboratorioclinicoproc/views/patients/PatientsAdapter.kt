package com.gameloop.laboratorioclinicoproc.views.patients

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gameloop.laboratorioclinicoproc.database.model.patient.Patient
import com.gameloop.laboratorioclinicoproc.databinding.ListItemPatientBinding

class PatientsAdapter(private val clickHandler: ClickHandler)
    : ListAdapter<Patient, PatientsAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder private constructor(private val binding: ListItemPatientBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(patient: Patient, clickHandler: ClickHandler) {
            binding.patient = patient
            binding.ivDelete.setOnClickListener { clickHandler.onDelete(patient) }
            binding.root.setOnClickListener { clickHandler.onOpen(patient) }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val binding = ListItemPatientBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return ViewHolder(binding)
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<Patient>() {
        override fun areItemsTheSame(oldItem: Patient, newItem: Patient): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Patient, newItem: Patient): Boolean {
            return oldItem == newItem
        }
    }

    interface ClickHandler {
        fun onOpen(patient: Patient) {}
        fun onDelete(patient: Patient) {}
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
        = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
        = holder.bind(getItem(position), clickHandler)
}