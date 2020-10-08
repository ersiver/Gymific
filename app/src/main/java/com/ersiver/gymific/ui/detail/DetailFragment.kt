package com.ersiver.gymific.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ersiver.gymific.R
import com.ersiver.gymific.databinding.FragmentDetailBinding
import com.ersiver.gymific.model.Workout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private val detailViewModel: DetailViewModel by viewModels()
    private lateinit var menuItem: MenuItem
    private val args: DetailFragmentArgs by navArgs()
    private lateinit var binding: FragmentDetailBinding
    private val workout: Workout by lazy {
        args.workout
    }
    private lateinit var toolbar: Toolbar
    private var pausedTime: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        binding.apply {
            viewModel = detailViewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupToolbarWithNavigation()
        onOptionsItemSelected()
        detailViewModel.start(workout.id)

        detailViewModel.workout.observe(viewLifecycleOwner) { workout ->
            updateMenuItemIcon(workout.isSaved)
        }

        detailViewModel.workoutTimeMillis.observe(viewLifecycleOwner) { workoutTimeMillis ->
            binding.workoutProgress.setDuration(workoutTimeMillis)
        }

        detailViewModel.savedPausedTime.observe(viewLifecycleOwner) { savedPausedTime ->
            detailViewModel.manageTimer(savedPausedTime)
        }

        detailViewModel.runningTime.observe(viewLifecycleOwner) {
            binding.workoutProgress.updateProgressBar(it)
        }

        detailViewModel.pausedWorkoutTimeMillis.observe(viewLifecycleOwner) {
            pausedTime = it
        }
    }

    private fun setupToolbarWithNavigation() {
        toolbar = binding.toolbarDetail
        toolbar.navigationContentDescription = resources.getString(R.string.navigate_up)
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun updateMenuItemIcon(saved: Boolean) {
        menuItem = toolbar.menu.getItem(0)
        val iconDrawable =
            if (saved) R.drawable.ic_favourite else R.drawable.ic_add_favourite
        menuItem.setIcon(iconDrawable)
    }

    private fun onOptionsItemSelected() {
        toolbar.setOnMenuItemClickListener {
            detailViewModel.setFavourite(workout)
            true
        }
    }

    override fun onPause() {
        super.onPause()
        detailViewModel.savePausedTime(workout.id, pausedTime)
    }
}