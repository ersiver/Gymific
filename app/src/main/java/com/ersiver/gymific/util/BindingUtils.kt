package com.ersiver.gymific.util

import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ersiver.gymific.model.Workout
import com.ersiver.gymific.model.WorkoutCategory
import com.ersiver.gymific.ui.common.WorkoutAdapter
import com.ersiver.gymific.ui.detail.TimerStatus
import com.ersiver.gymific.ui.home.WorkoutCategoryAdapter

/**
 * Binding adapter used to to display workout time in the descriptions.
 */
@BindingAdapter("timeFormatted")
fun TextView.bindWorkTime(time: Long) {
    val toBind = "${time}sec"
    text = toBind
}

/**
 * Binding adapter used to to display a matching workout image.
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

/**
 * Binding adapter used to submit list of categories to the [WorkoutCategoryAdapter].
 */
@BindingAdapter("categoryListData")
fun RecyclerView.bindList(list: List<WorkoutCategory>?) {
   val adapter = adapter as WorkoutCategoryAdapter
    adapter.submitList(list)
}


/**
 * Binding adapter used to manage behaviour of
 * the playButton in the DetailFragment.
 */
@BindingAdapter("playButtonEnable")
fun ImageButton.playButtonBehaviour(status: TimerStatus?) {
    when (status) {
        TimerStatus.OFF -> enabledAndOpaque()
        TimerStatus.ON -> disabledAndFade()
        TimerStatus.PAUSED -> enabledAndOpaque()
    }
}

/**
 * Binding adapter used to manage behaviour of
 * the stopButton in the DetailFragment.
 */
@BindingAdapter("stopButtonEnable")
fun ImageButton.stopButtonBehaviour(status: TimerStatus?) {
    when (status) {
        TimerStatus.OFF -> disabledAndFade()
        TimerStatus.ON -> enabledAndOpaque()
        TimerStatus.PAUSED -> enabledAndOpaque()
    }
}

/**
 * Binding adapter used to manage behaviour of
 * the pauseButton in the DetailFragment.
 */
@BindingAdapter("pauseButtonEnable")
fun ImageButton.pauseButtonBehaviour(status: TimerStatus?) {
    when (status) {
        TimerStatus.OFF -> disabledAndFade()
        TimerStatus.ON -> enabledAndOpaque()
        TimerStatus.PAUSED -> disabledAndFade()
    }
}