package com.gameloop.laboratorioclinicoproc.views.labtestcategoriesdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gameloop.laboratorioclinicoproc.databinding.FragmentLabTestCategoryListBinding

class LabCategoryDetailFragment : Fragment() {

    private lateinit var binding: FragmentLabTestCategoryListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLabTestCategoryListBinding.inflate(layoutInflater)

        return binding.root
    }
}