package com.gameloop.laboratorioclinicoproc.views.patients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gameloop.laboratorioclinicoproc.database.LabDatabase
import com.gameloop.laboratorioclinicoproc.databinding.FragmentMyPatientsBinding
import com.gameloop.laboratorioclinicoproc.views.home.HomeFragmentDirections

class MyPatientsFragment : Fragment() {
    private lateinit var binding: FragmentMyPatientsBinding

    private val viewModel: MyPatientsViewModel by lazy {
        val app = requireActivity().application
        val dao = LabDatabase.getInstance(app).patients
        val factory = MyPatientsViewModelFactory(dao)
        ViewModelProvider(this, factory)[MyPatientsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyPatientsBinding.inflate(layoutInflater)
        binding.lifecycleOwner = viewLifecycleOwner

        setUpPatientsView()

        return binding.root
    }

    private fun setUpPatientsView() {
        val adapter = PatientsAdapter()
        binding.rvPatients.adapter = adapter
        binding.rvPatients.layoutManager = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.VERTICAL,
            false
        )

        viewModel.patients.observe(viewLifecycleOwner) {
            it?.let {
                adapter.submitList(it)
            }
        }
    }
}