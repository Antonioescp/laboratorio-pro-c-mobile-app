package com.gameloop.laboratorioclinicoproc.views.labtestcategories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gameloop.laboratorioclinicoproc.database.model.labtestcategory.LabTestCategory
import com.gameloop.laboratorioclinicoproc.databinding.ListItemLabTestCategoryBinding

class LabTestCategoryAdapter
    : ListAdapter<LabTestCategory, LabTestCategoryAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder(private val binding: ListItemLabTestCategoryBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: LabTestCategory) {
            binding.category = category
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ListItemLabTestCategoryBinding
                    .inflate(inflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<LabTestCategory>() {
        override fun areItemsTheSame(oldItem: LabTestCategory, newItem: LabTestCategory): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(
            oldItem: LabTestCategory,
            newItem: LabTestCategory
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
        = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
        = holder.bind(getItem(position))

}