package com.gameloop.laboratorioclinicoproc.views.appointments.detail.dialogs.selectlabtest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gameloop.laboratorioclinicoproc.database.model.labtest.LabTest
import com.gameloop.laboratorioclinicoproc.databinding.DialogItemLabTestBinding

class DialogLabTestAdapter(
    private val listener: Listener
) : ListAdapter<LabTest, DialogLabTestAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder private constructor(
        private val binding: DialogItemLabTestBinding,
        private val listener: Listener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(labTest: LabTest) {
            binding.labTest = labTest
            setUpSelectableBehaviour(labTest)
        }

        private fun setUpSelectableBehaviour(labTest: LabTest) {
            binding.root.setOnClickListener {
                binding.cbSelected.isChecked = !binding.cbSelected.isChecked
            }

            binding.cbSelected.setOnCheckedChangeListener { _, isChecked ->
                listener.onSelectChange(labTest, isChecked)
            }
        }

        companion object {
            fun from(parent: ViewGroup, listener: Listener): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = DialogItemLabTestBinding.inflate(
                    inflater,
                    parent,
                    false
                )
                return ViewHolder(binding, listener)
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<LabTest>() {
        override fun areItemsTheSame(oldItem: LabTest, newItem: LabTest): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: LabTest, newItem: LabTest): Boolean {
            return oldItem == newItem
        }
    }

    interface Listener {
        fun onSelectChange(labTest: LabTest, isSelected: Boolean) {}
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}