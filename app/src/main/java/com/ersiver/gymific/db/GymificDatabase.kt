package com.ersiver.gymific.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ersiver.gymific.model.Workout
import com.ersiver.gymific.model.WorkoutCategory

@Database(entities = [Workout::class, WorkoutCategory::class], version = 1)
abstract class GymificDatabase : RoomDatabase() {
    abstract val workoutDao: WorkoutDao
    abstract val categoryDao: WorkoutCategoryDao
}