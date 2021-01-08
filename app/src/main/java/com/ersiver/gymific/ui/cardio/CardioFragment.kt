package com.ersiver.gymific.ui.cardio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.ersiver.gymific.R
import com.ersiver.gymific.databinding.FragmentCardioBinding
import com.ersiver.gymific.ui.common.WorkoutAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CardioFragment : Fragment() {
    private val cardioViewModel: CardioViewModel by navGraphViewModels(R.id.mobile_navigation) { defaultViewModelProviderFactory }
    private lateinit var binding: FragmentCardioBinding
    private lateinit var adapter: WorkoutAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCardioBinding.inflate(inflater, container, false)
        binding.apply {
            viewModel = cardioViewModel
            lifecycleOwner = viewLifecycleOwner
        }
        setupRecyclerView()
        return binding.root
    }

    private fun setupRecyclerView() {
        adapter = WorkoutAdapter(true)
        binding.apply {
            cardioList.adapter = adapter
            val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            cardioList.addItemDecoration(decoration)
        }
    }
}