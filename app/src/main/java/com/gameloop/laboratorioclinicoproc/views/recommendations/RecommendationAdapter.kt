package com.gameloop.laboratorioclinicoproc.views.recommendations

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.marginBottom
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gameloop.laboratorioclinicoproc.databinding.ListItemRecommendationBinding

class RecommendationAdapter : ListAdapter<String, RecommendationAdapter.ViewHolder>(DiffCallback())  {

    class ViewHolder private constructor(private val binding: ListItemRecommendationBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(recommendation: String) {
            binding.recommendation = recommendation
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ListItemRecommendationBinding.inflate(
                    inflater,
                    parent,
                    false
                )
                return ViewHolder(binding)
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
        = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
        = holder.bind(getItem(position))
}