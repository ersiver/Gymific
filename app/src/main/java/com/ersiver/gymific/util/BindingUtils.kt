package com.ersiver.gymific.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ersiver.gymific.model.Workout
import com.ersiver.gymific.ui.common.WorkoutAdapter


/**
 * Binding adapter used to to display workout time with sec at the end.
 */
@BindingAdapter("timeFormatted")
fun TextView.bindWorkTime(time: Long) {
    val toBind = "${time}sec"
    text = toBind
}


/**
 * Binding adapter used to to display a proper workout image.
 */
@BindingAdapter("srcImage")
fun ImageView.bindImage(code: String?) {
    code?.let {
        setImageResource(it.asDrawableId())
    }
}

/**
 * Binding adapter used to submit list of workouts to the [WorkoutAdapter].
 */
@BindingAdapter("listData")
fun RecyclerView.bindRecyclerView(list: List<Workout>?) {
    val adapter = adapter as WorkoutAdapter
    adapter.submitList(list)
}