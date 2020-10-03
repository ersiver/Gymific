package com.ersiver.gymific.ui.common

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ersiver.gymific.model.Workout
import timber.log.Timber

private const val LIST_VIEW_TYPE = 0
private const val GRID_VIEW_TYPE = 1

class WorkoutAdapter(private val isListView: Boolean) :
    ListAdapter<Workout, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    init {
        Timber.i("Init WorkoutsAdapter")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == LIST_VIEW_TYPE) {
            ListItemViewHolder.from(parent)
        } else {
            GridItemViewHolder.from(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val workout = getItem(position)
        if (holder is ListItemViewHolder)
            (holder as ListItemViewHolder).bind(workout)
        else (holder as GridItemViewHolder).bind(workout)
    }

    override fun getItemViewType(position: Int): Int {
        return if (isListView) LIST_VIEW_TYPE else GRID_VIEW_TYPE
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Workout> =
            object : DiffUtil.ItemCallback<Workout>() {
                override fun areItemsTheSame(oldItem: Workout, newItem: Workout) =
                    oldItem.id == newItem.id

                override fun areContentsTheSame(oldItem: Workout, newItem: Workout) =
                    oldItem.id == newItem.id
            }
    }
}