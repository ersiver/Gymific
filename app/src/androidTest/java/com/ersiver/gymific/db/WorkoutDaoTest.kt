package com.ersiver.gymific.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.ersiver.gymific.model.Workout
import com.ersiver.gymific.util.MainCoroutinesRule
import com.ersiver.gymific.util.TestUtil
import com.ersiver.gymific.util.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@SmallTest
@RunWith(AndroidJUnit4::class)
class WorkoutDaoTest : LocalDatabase() {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutinesRule = MainCoroutinesRule()

    private lateinit var workoutDao: WorkoutDao
    private lateinit var workout: Workout
    private lateinit var workouts: List<Workout>

    @Before
    fun init() = runBlocking {
        workoutDao = database.workoutDao

        //Start each test with list of workouts
        workout = TestUtil.createWorkout(id = 1, isSaved = false)
        workouts = listOf(workout)

    }

    @Test
    fun insertAndLoadWorkouts() = runBlocking {
        //Insert the list to db
        workoutDao.insertAll(workouts)

        //Get flow data from db and verify it matches the inserted list.
        val workoutsFlow: Flow<List<Workout>> = workoutDao.getWorkouts()
        val loadedWorkouts: List<Workout> = workoutsFlow.first()
        assertThat(loadedWorkouts.size, `is`(workouts.size))
        assertThat(loadedWorkouts[0].id, `is`(workouts[0].id))
        assertThat(loadedWorkouts[0].title, `is`(workouts[0].title))
        assertThat(loadedWorkouts[0].instruction, `is`(workouts[0].instruction))
        assertThat(loadedWorkouts[0].category, `is`(workouts[0].category))
        assertThat(loadedWorkouts[0].time, `is`(workouts[0].time))
        assertThat(loadedWorkouts[0].isSaved, `is`(workouts[0].isSaved))
        assertThat(loadedWorkouts[0].isRecommended, `is`(workouts[0].isRecommended))
    }

    @Test
    fun insertAndLoadById() = runBlocking {
        //Insert the list to db
        workoutDao.insertAll(workouts)

        //Get workout by id from db
        val loaded: Workout = workoutDao.getWorkout(1).getOrAwaitValue()

        //Verify that data loaded matches inserted workout
        assertThat(loaded.id, `is`(workout.id))
        assertThat(loaded.title, `is`(workout.title))
        assertThat(loaded.category, `is`(workout.category))
        assertThat(loaded.instruction, `is`(workout.instruction))
        assertThat(loaded.isSaved, `is`(workout.isSaved))
        assertThat(loaded.isRecommended, `is`(workout.isRecommended))
        assertThat(loaded.time, `is`(workout.time))
    }

    @Test
    fun updateWorkout() = runBlocking {
        //Insert the list to db
        workoutDao.insertAll(workouts)

        //Get workout by id and assert the value of isSaved is false
        var loaded: Workout = workoutDao.getWorkout(1).getOrAwaitValue()
        assertThat(loaded.isSaved, `is`(false))

        //Update the workout
        workout.isSaved = true
        workoutDao.update(workout)

        //Get workout by id from db and assert the value of isSaved is now true.
        loaded = workoutDao.getWorkout(1).getOrAwaitValue()
        assertThat(loaded.isSaved, `is`(true))
    }
}