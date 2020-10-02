package com.ersiver.gymific.util

import com.ersiver.gymific.model.Workout
import com.ersiver.gymific.model.WorkoutCategory

object TestUtil {

    fun createWorkoutCategory() =
        WorkoutCategory("test_id", "Cardio", "Test overview", "Test description")


    fun createWorkout(id: Int, isSaved: Boolean) = Workout(
        id = id,
        title = "Half WindowInsets.Side Plank",
        category = "Arms",
        time = 30,
        iconCode = "arms_b",
        instruction = "Start with left arm under shoulder, feet staggered, hips lifted, glutes and abs tight. Lift arm straight up to ceiling, hold position and breathe.",
        isSaved = isSaved,
        timeSaved = 0,
        isRecommended = false
    )
}
