package com.ersiver.gymific.ui.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.ersiver.gymific.R
import com.ersiver.gymific.databinding.FragmentFavouriteBinding
import com.ersiver.gymific.ui.common.WorkoutAdapter
import com.ersiver.gymific.util.BY_CATEGORY
import com.ersiver.gymific.util.BY_DATE
import com.ersiver.gymific.util.BY_TIME
import com.ersiver.gymific.util.BY_TITLE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteFragment : Fragment() {
    private val favouriteViewModel: FavouriteViewModel by navGraphViewModels(R.id.mobile_navigation) { defaultViewModelProviderFactory }
    private lateinit var binding: FragmentFavouriteBinding
    private lateinit var adapter: WorkoutAdapter
    private lateinit var toolbar: Toolbar
    private val checkedItems: MutableList<MenuItem> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        binding.apply {
            viewModel = favouriteViewModel
            lifecycleOwner = viewLifecycleOwner
        }
        onOptionsItemSelected()
        setupRecyclerView()

        favouriteViewModel.sortOrder.observe(viewLifecycleOwner) { sortOrderName ->
            setChecked(sortOrderName)
        }
        return binding.root
    }

    private fun onOptionsItemSelected() {
        toolbar = binding.toolbarFavourite

        toolbar.setOnMenuItemClickListener { item ->
            val order = when (item.itemId) {
                R.id.sort_by_date -> BY_DATE
                R.id.sort_by_name -> BY_TITLE
                R.id.sort_by_category -> BY_CATEGORY
                else -> BY_TIME
            }
            favouriteViewModel.updateSortOrder(order)
            true
        }
    }

    private fun setupRecyclerView() {
        adapter = WorkoutAdapter(true)
        binding.apply {
            favouriteList.adapter = adapter
            val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            favouriteList.addItemDecoration(decoration)
        }
    }

    /**
     * The method is invoked not only when the user selects
     * item but also on start, to retrieve last chosen option.
     */
    private fun setChecked(sortOrderName: String) {
        val menu = toolbar.menu
        val selectedItem = when (sortOrderName) {
            BY_DATE -> menu.getItem(0)
            BY_TITLE -> menu.getItem(1)
            BY_CATEGORY -> menu.getItem(2)
            else -> menu.getItem(3)
        }
        selectedItem.isChecked = !selectedItem.isChecked
    }
}