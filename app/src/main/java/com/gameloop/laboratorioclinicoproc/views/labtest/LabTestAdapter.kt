package com.gameloop.laboratorioclinicoproc.views.labtest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gameloop.laboratorioclinicoproc.database.model.labtest.LabTest
import com.gameloop.laboratorioclinicoproc.database.model.labtestcategory.LabTestCategory
import com.gameloop.laboratorioclinicoproc.databinding.ListItemLabTestBinding

class LabTestAdapter(private val listener: ClickListener)
    : ListAdapter<LabTest, LabTestAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder private constructor (private val binding: ListItemLabTestBinding)
        : RecyclerView.ViewHolder(binding.root) {

            fun bind(labTest: LabTest, listener: ClickListener) {
                binding.labTest = labTest
                binding.btnDetails.setOnClickListener { listener.onSeeMore(labTest) }
            }

            companion object {
                fun from(parent: ViewGroup): ViewHolder {
                    val inflater = LayoutInflater.from(parent.context)
                    val binding = ListItemLabTestBinding.inflate(
                        inflater,
                        parent,
                        false
                    )
                    return ViewHolder(binding)
                }
            }
    }

    private class DiffCallback : DiffUtil.ItemCallback<LabTest>() {
        override fun areItemsTheSame(oldItem: LabTest, newItem: LabTest): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: LabTest, newItem: LabTest): Boolean {
            return areValueMembersTheSame(oldItem, newItem)
                    && areRecommendationsTheSame(oldItem.recommendations, newItem.recommendations)
                    && areCategoriesTheSame(oldItem.category, newItem.category)
        }

        private fun areValueMembersTheSame(oldItem: LabTest, newItem: LabTest): Boolean {
            return oldItem.title == newItem.title
                    && oldItem.price == newItem.price
        }

        private fun areRecommendationsTheSame(oldItem: List<String>, newItem: List<String>): Boolean {
            return oldItem.size == newItem.size
                    && newItem.containsAll(oldItem)
        }

        private fun areCategoriesTheSame(oldItem: LabTestCategory, newItem: LabTestCategory)
        : Boolean {
            return oldItem.title == newItem.title
                    && oldItem.imgSrc == newItem.imgSrc
                    && areRecommendationsTheSame(oldItem.recommendations, newItem.recommendations)
        }
    }

    interface ClickListener {
        fun onSeeMore(labTest: LabTest)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
        = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
        = holder.bind(getItem(position), listener)
}