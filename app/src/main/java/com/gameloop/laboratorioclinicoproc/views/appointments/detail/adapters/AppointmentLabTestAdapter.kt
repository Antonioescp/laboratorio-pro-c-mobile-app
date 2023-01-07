package com.gameloop.laboratorioclinicoproc.views.appointments.detail.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gameloop.laboratorioclinicoproc.database.model.labtest.LabTest
import com.gameloop.laboratorioclinicoproc.databinding.ListItemAppointmentLabTestBinding

class AppointmentLabTestAdapter(private val listener: Listener)
    : ListAdapter<LabTest, AppointmentLabTestAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder private constructor(private val binding: ListItemAppointmentLabTestBinding)
        : RecyclerView.ViewHolder(binding.root) {
            fun bind(labTest: LabTest, listener: Listener) {
                binding.labTest = labTest
                binding.btnDetails.setOnClickListener { listener.onSee(labTest) }
                binding.btnDelete.setOnClickListener { listener.onDelete(labTest) }
            }

            companion object {
                fun from(parent: ViewGroup): ViewHolder {
                    val inflater = LayoutInflater.from(parent.context)
                    val binding = ListItemAppointmentLabTestBinding
                        .inflate(inflater, parent, false)
                    return ViewHolder(binding)
                }
            }
        }

    class DiffCallback : DiffUtil.ItemCallback<LabTest>() {
        override fun areItemsTheSame(oldItem: LabTest, newItem: LabTest): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: LabTest, newItem: LabTest): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
        = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
        = holder.bind(getItem(position), listener)

    interface Listener {
        fun onSee(labTest: LabTest) {}
        fun onDelete(labTest: LabTest) {}
    }
}