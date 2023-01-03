package com.gameloop.laboratorioclinicoproc.views.labtestdetail

import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import com.gameloop.laboratorioclinicoproc.R
import com.gameloop.laboratorioclinicoproc.databinding.FragmentLabTestDetailBinding
import com.gameloop.laboratorioclinicoproc.views.recommendations.RecommendationAdapter
import timber.log.Timber

class LabTestDetailFragment : Fragment() {

    private lateinit var binding: FragmentLabTestDetailBinding
    private val args by navArgs<LabTestDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLabTestDetailBinding.inflate(inflater)
        binding.labTest = args.labTest

        setUpRecommendationsList()

        return binding.root
    }

    private fun setUpRecommendationsList() {
        val adapter = RecommendationAdapter()
        adapter.submitList(args.labTest.recommendations)
        binding.rvRecommendations.adapter = adapter

        val arrowRight = ContextCompat
            .getDrawable(requireContext(), R.drawable.ic_baseline_keyboard_arrow_right_24)
        val arrowDown = ContextCompat
            .getDrawable(requireContext(), R.drawable.ic_baseline_keyboard_arrow_down_24)

        binding.tvRecommendations.setOnClickListener {
            TransitionManager.beginDelayedTransition(binding.cvRecommendations, AutoTransition())
            if (binding.rvRecommendations.visibility == View.VISIBLE) {

                binding.rvRecommendations.visibility = View.GONE

                binding.tvRecommendations.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    null, null, arrowRight, null
                )

            } else {

                binding.rvRecommendations.visibility = View.VISIBLE

                binding.tvRecommendations.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    null, null, arrowDown, null
                )

            }
        }
    }
}