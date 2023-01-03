package com.gameloop.laboratorioclinicoproc.views.patients

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.MenuProvider
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gameloop.laboratorioclinicoproc.R
import com.gameloop.laboratorioclinicoproc.database.LabDatabase
import com.gameloop.laboratorioclinicoproc.database.model.patient.Patient
import com.gameloop.laboratorioclinicoproc.databinding.FragmentMyPatientsBinding

class MyPatientsFragment : Fragment(), MenuProvider {
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
        binding.viewModel = viewModel

        setUpPatientsView()
        setUpAddPatientButton()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        requireActivity().addMenuProvider(this)
    }

    override fun onStop() {
        super.onStop()
        requireActivity().removeMenuProvider(this)
    }

    private fun setUpAddPatientButton() {
        viewModel.eventNavigateToAddPatient.observe(viewLifecycleOwner) { shouldNavigate ->
            shouldNavigate?.let {
                if (shouldNavigate) {
                    val action = MyPatientsFragmentDirections
                        .actionMyPatientsFragmentToSignUpFragment()
                    findNavController().navigate(action)
                    viewModel.onNavigateToAddPatientComplete()
                }
            }
        }
    }

    private fun setUpPatientsView() {
        val adapter = PatientsAdapter(object: PatientsAdapter.ClickHandler{
            override fun onDelete(patient: Patient) {
                viewModel.deletePatient(patient)
            }

            override fun onOpen(patient: Patient) {
                val action = MyPatientsFragmentDirections
                    .actionMyPatientsFragmentToSignUpFragment(patient)
                findNavController().navigate(action)
            }
        })

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

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_patients, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.miAddPatient -> {
                val action = MyPatientsFragmentDirections
                    .actionMyPatientsFragmentToSignUpFragment(null)
                findNavController().navigate(action)
                true
            }
            else -> false
        }
    }
}