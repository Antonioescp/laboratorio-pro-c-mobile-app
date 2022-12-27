package com.gameloop.laboratorioclinicoproc.views.signup

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.gameloop.laboratorioclinicoproc.R
import com.gameloop.laboratorioclinicoproc.database.LabDatabase
import com.gameloop.laboratorioclinicoproc.databinding.FragmentSignUpBinding
import com.gameloop.laboratorioclinicoproc.validateEmpty
import com.gameloop.laboratorioclinicoproc.validatePredicate
import com.gameloop.laboratorioclinicoproc.validateRegex

class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding

    private val viewModel: SignUpViewModel by lazy {
        val app = requireActivity().application
        val patientDao = LabDatabase.getInstance(app).patients
        val factory = SignUpViewModelFactory(patientDao)
        ViewModelProvider(this, factory)[SignUpViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(layoutInflater)

        // Passing ViewModel for DataBinding
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // Listening to sign up button click
        viewModel.eventSignUp.observe(viewLifecycleOwner) { event ->
            if (event) {
                if (validateFields()) {
                    viewModel.insertPatient()
                    findNavController().navigateUp()
                }
                viewModel.onSignUpComplete()
            }
        }

        return binding.root
    }

    private fun validateFields(): Boolean {
        var valid = validateName()
        valid = validateAge() && valid
        valid = validateEmail() && valid
        return valid
    }

    private fun validateName(): Boolean {
        var valid = binding.tietName.validateEmpty(getString(R.string.cant_be_empty))
        valid = valid && binding.tietName
            .validateRegex(Regex("\\s"), "Introduzca un nombre y un apellido")
        return valid
    }

    private fun validateAge(): Boolean {
        var valid = binding.tietAge
            .validateEmpty(getString(R.string.cant_be_empty))

        val minAge = 1
        valid = valid && binding.tietAge.validatePredicate("Debe ser un numero positivo") {
            val age = it.text.toString().toIntOrNull()
            age != null && age >= minAge
        }

        return valid
    }

    private fun validateEmail(): Boolean {
        return binding.tietEmail.validateEmpty(getString(R.string.cant_be_empty))
                && binding.tietEmail.validateRegex(
                    Patterns.EMAIL_ADDRESS.toRegex(),
                    "Ingresa un correo válido"
                )
    }
}