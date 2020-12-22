package com.ersiver.gymific.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels
import com.ersiver.gymific.R
import com.ersiver.gymific.databinding.FragmentHomeBinding
import com.ersiver.gymific.ui.common.WorkoutAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val homeViewModel: HomeViewModel by navGraphViewModels(R.id.mobile_navigation) { defaultViewModelProviderFactory }
    private lateinit var binding: FragmentHomeBinding
    private lateinit var workoutAdapter: WorkoutAdapter
    private lateinit var categoryAdapter: WorkoutCategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.apply {
            viewModel = homeViewModel
            lifecycleOwner = viewLifecycleOwner
        }
        setupWorkoutRecyclerView()
        setupCategoryRecyclerView()
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
}