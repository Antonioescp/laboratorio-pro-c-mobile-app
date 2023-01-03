package com.gameloop.laboratorioclinicoproc.views.labtest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.gameloop.laboratorioclinicoproc.database.model.labtest.LabTest
import com.gameloop.laboratorioclinicoproc.databinding.FragmentLabTestBinding

class LabTestFragment : Fragment() {
    private lateinit var binding: FragmentLabTestBinding

    private val args by navArgs<LabTestFragmentArgs>()

    private val viewModel: LabTestViewModel by lazy {
        val factory = LabTestViewModelFactory(args.labTestCategoryTitle)
        ViewModelProvider(this, factory)[LabTestViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLabTestBinding.inflate(inflater)

        setUpLabTestList()

        return binding.root
    }

    private fun setUpLabTestList() {
        val adapter = LabTestAdapter(object: LabTestAdapter.ClickListener{
            override fun onSeeMore(labTest: LabTest) {
                val action = LabTestFragmentDirections
                    .actionLabTestFragmentToLabTestDetailFragment(labTest)
                findNavController().navigate(action)
            }
        })

        binding.rvLabTests.adapter = adapter

        viewModel.availableTests.observe(viewLifecycleOwner) {
            it?.let {
                adapter.submitList(it)
            }
        }
    }
}