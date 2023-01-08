package com.gameloop.laboratorioclinicoproc.views.appointments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gameloop.laboratorioclinicoproc.database.model.appointment.AppointmentNetwork
import com.gameloop.laboratorioclinicoproc.databinding.ListItemAppointmentBinding

class AppointmentAdapter(
    private val listener: Listener
) : ListAdapter<AppointmentNetwork, AppointmentAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder private constructor(
        private val binding: ListItemAppointmentBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(appointmentNetwork: AppointmentNetwork, listener: Listener) {
            binding.appointment = appointmentNetwork
            binding.root.setOnClickListener(listener::onClick)
            binding.btnAction.setOnClickListener { listener.onAction(appointmentNetwork) }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemAppointmentBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
                return ViewHolder(binding)
            }
        }

    }

    private class DiffCallback : DiffUtil.ItemCallback<AppointmentNetwork>() {
        override fun areItemsTheSame(
            oldItem: AppointmentNetwork,
            newItem: AppointmentNetwork
        ): Boolean {
            return oldItem.description == newItem.description
        }

        override fun areContentsTheSame(
            oldItem: AppointmentNetwork,
            newItem: AppointmentNetwork
        ): Boolean {
            return oldItem == newItem
        }

    }

    interface Listener {
        fun onClick(view: View) {}
        fun onAction(appointmentNetwork: AppointmentNetwork) {}
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }
}