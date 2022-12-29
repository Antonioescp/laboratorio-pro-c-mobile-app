package com.gameloop.laboratorioclinicoproc.views.labtestcategoriesdetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navArgs
import com.gameloop.laboratorioclinicoproc.R
import com.gameloop.laboratorioclinicoproc.database.model.labtestcategory.LabTestCategory
import com.gameloop.laboratorioclinicoproc.databinding.ActivityLabCategoryDetailBinding
import com.gameloop.laboratorioclinicoproc.views.recommendations.RecommendationAdapter

class LabCategoryDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLabCategoryDetailBinding
    private val args by navArgs<LabCategoryDetailActivityArgs>()

    private val viewModel: LabCategoryDetailViewModel by lazy {
        val factory = LabCategoryDetailViewModelFactory(args.categoryTitle)
        ViewModelProvider(this, factory)[LabCategoryDetailViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_lab_category_detail)
        binding.category = LabTestCategory()
        viewModel.currentCategory.observe(this) { it?.let { binding.category = it } }

        setUpRecommendationsList()
    }

    private fun setUpRecommendationsList() {
        val adapter = RecommendationAdapter()

        viewModel.currentCategory.observe(this) { updatedCategory ->
            updatedCategory?.let {
                adapter.submitList(it.recommendations)
            }
        }

        binding.rvRecommendations.adapter = adapter
    }
}