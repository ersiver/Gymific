package com.ersiver.gymific.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ersiver.gymific.model.Workout
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(list: List<Workout>)

    @Update
    suspend fun update(workout: Workout)

    @Query("SELECT*FROM workout_table")
    fun getWorkouts(): Flow<List<Workout>>

    @Query("SELECT * FROM workout_table WHERE id LIKE :id")
     fun getWorkout(id: Int): LiveData<Workout>
}