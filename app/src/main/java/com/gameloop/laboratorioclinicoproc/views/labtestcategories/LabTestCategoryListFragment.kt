package com.gameloop.laboratorioclinicoproc.views.labtestcategories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.gameloop.laboratorioclinicoproc.database.LabDatabase
import com.gameloop.laboratorioclinicoproc.databinding.FragmentLabTestCategoryListBinding

class LabTestCategoryListFragment : Fragment() {
    private lateinit var binding: FragmentLabTestCategoryListBinding

    private val viewModel: LabTestCategoryListViewModel by lazy {
        val app = requireActivity().application
        val db = LabDatabase.getInstance(app)
        val factory = LabTestCategoryListViewModelFactory(db)
        ViewModelProvider(this, factory)[LabTestCategoryListViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLabTestCategoryListBinding.inflate(layoutInflater)

        setupLabTestList()

        return binding.root
    }

    private fun setupLabTestList() {
        val adapter = LabTestCategoryAdapter()
        binding.rvLabTests.adapter = adapter

        viewModel.categories.observe(viewLifecycleOwner) {
            it?.let { adapter.submitList(it) }
        }
    }
}