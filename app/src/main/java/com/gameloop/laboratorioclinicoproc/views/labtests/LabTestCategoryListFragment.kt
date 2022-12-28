package com.gameloop.laboratorioclinicoproc.views.labtests

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.gameloop.laboratorioclinicoproc.R
import com.gameloop.laboratorioclinicoproc.database.LabDatabase
import com.gameloop.laboratorioclinicoproc.databinding.FragmentLabTestCategoryListBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

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

        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            binding.tvTest.text = "$categories"
        }

        return binding.root
    }
}