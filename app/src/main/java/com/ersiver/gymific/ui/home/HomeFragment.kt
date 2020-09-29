package com.ersiver.gymific.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ersiver.gymific.databinding.FragmentHomeBinding
import com.ersiver.gymific.ui.common.WorkoutAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val homeViewModel: HomeViewModel by viewModels()
    private val binding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }
    private lateinit var workoutAdapter: WorkoutAdapter

    private lateinit var categoryAdapter: WorkoutCategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.apply {
            viewModel = homeViewModel
        }
        setupWorkoutRecyclerView()
        setupCategoryRecyclerView()
        subscribeRecommendedUi()
        subscribeCategoryUi()
        return binding.root
    }

    private fun setupCategoryRecyclerView() {
        categoryAdapter = WorkoutCategoryAdapter()
        binding.categoryList.adapter = categoryAdapter
    }

    private fun setupWorkoutRecyclerView() {
        workoutAdapter = WorkoutAdapter(false)
        binding.recommendedList.adapter = workoutAdapter
    }
    private fun subscribeCategoryUi() {
        homeViewModel.categoriesUiModel.observe(viewLifecycleOwner) {
            categoryAdapter.submitList(it.categories)
        }
    }

    private fun subscribeRecommendedUi() {
        homeViewModel.recommendedUiModel.observe(viewLifecycleOwner) {
            workoutAdapter.submitList(it.workouts)
        }
    }
}