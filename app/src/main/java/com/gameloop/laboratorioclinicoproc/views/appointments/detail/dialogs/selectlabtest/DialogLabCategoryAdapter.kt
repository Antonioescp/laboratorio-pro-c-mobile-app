package com.gameloop.laboratorioclinicoproc.views.appointments.detail.dialogs.selectlabtest

import android.graphics.drawable.Drawable
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gameloop.laboratorioclinicoproc.R
import com.gameloop.laboratorioclinicoproc.database.model.labtest.LabTest
import com.gameloop.laboratorioclinicoproc.database.model.labtestcategory.LabTestCategory
import com.gameloop.laboratorioclinicoproc.databinding.DialogItemLabCategoryBinding
import com.gameloop.laboratorioclinicoproc.network.LabNetworkService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DialogLabCategoryAdapter(
    private val listener: DialogLabTestAdapter.Listener
) : ListAdapter<LabTestCategory, DialogLabCategoryAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder private constructor(
        private val binding: DialogItemLabCategoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private var isExpanded = false
        private lateinit var listener: DialogLabTestAdapter.Listener

        fun bind(category: LabTestCategory, listener: DialogLabTestAdapter.Listener) {
            binding.category = category
            binding.tvTitle.setOnClickListener { toggleExpand() }

            this.listener = listener

            setUpLabTestsList(category)
        }

        private fun setUpLabTestsList(category: LabTestCategory) {
            val adapter = DialogLabTestAdapter(listener)
            binding.rvLabTests.adapter = adapter

            // Getting lab tests for current category
            CoroutineScope(Dispatchers.IO).launch {
                val tests = LabNetworkService.instance.getNonLiveLabTestsByCategory(category.title)
                withContext(Dispatchers.Main) {
                    adapter.submitList(tests)
                }
            }
        }

        private fun toggleExpand() {
            TransitionManager.beginDelayedTransition(binding.cvMainContainer, AutoTransition())
            val drawable: Drawable?
            if (isExpanded) {
                binding.rvLabTests.visibility = View.GONE
                drawable = ContextCompat
                    .getDrawable(binding.root.context, R.drawable.ic_baseline_keyboard_arrow_right_24)
            } else {
                binding.rvLabTests.visibility = View.VISIBLE
                drawable = ContextCompat
                    .getDrawable(binding.root.context, R.drawable.ic_baseline_keyboard_arrow_down_24)
            }

            binding.tvTitle.setCompoundDrawablesRelativeWithIntrinsicBounds(
                null, null, drawable, null
            )
            isExpanded = !isExpanded
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = DialogItemLabCategoryBinding.inflate(
                    inflater,
                    parent,
                    false
                )
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }

}