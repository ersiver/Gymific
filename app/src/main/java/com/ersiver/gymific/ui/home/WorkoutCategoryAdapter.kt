package com.ersiver.gymific.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ersiver.gymific.databinding.CategoryViewItemBinding
import com.ersiver.gymific.model.WorkoutCategory

class WorkoutCategoryAdapter() : ListAdapter<WorkoutCategory, WorkoutCategoryViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutCategoryViewHolder {
        return WorkoutCategoryViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: WorkoutCategoryViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<WorkoutCategory> =
            object : DiffUtil.ItemCallback<WorkoutCategory>() {
                override fun areItemsTheSame(oldItem: WorkoutCategory, newItem: WorkoutCategory) =
                    oldItem == newItem

                override fun areContentsTheSame(
                    oldItem: WorkoutCategory,
                    newItem: WorkoutCategory
                ) = oldItem.id == newItem.id
            }
    }
}


class WorkoutCategoryViewHolder(private val binding: CategoryViewItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(workoutCategory: WorkoutCategory) {
        binding.run {
            category = workoutCategory
            executePendingBindings()
        }
    }

    companion object {
        fun from(parent: ViewGroup): WorkoutCategoryViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = CategoryViewItemBinding.inflate(layoutInflater, parent, false)
            return WorkoutCategoryViewHolder(binding)
        }
    }

}