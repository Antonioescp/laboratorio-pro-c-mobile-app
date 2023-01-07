package com.gameloop.laboratorioclinicoproc.views.appointments.detail.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.gameloop.laboratorioclinicoproc.database.model.labtest.LabTest
import com.gameloop.laboratorioclinicoproc.databinding.ListItemAppointmentPatientBinding

class AppointmentAdapter {
    class ViewHolder private constructor(private val binding: ListItemAppointmentPatientBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(values: List<Pair<LabTest, String>>) {
            val adapter = AppointmentLabTestAdapter(object: AppointmentLabTestAdapter.Listener {
                override fun onSee(labTest: LabTest) {
                    Toast.makeText(
                        binding.root.context,
                        "Should navigate to ${labTest.title}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onDelete(labTest: LabTest) {
                    Toast.makeText(
                        binding.root.context,
                        "Should delete ${labTest.title}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })

            binding.rvTests.adapter = adapter
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
}