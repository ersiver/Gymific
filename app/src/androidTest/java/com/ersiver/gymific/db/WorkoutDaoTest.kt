package com.ersiver.gymific.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.ersiver.gymific.util.TestUtil
import com.ersiver.gymific.util.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
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

    private lateinit var workoutDao: WorkoutDao

    @Before
    fun init() {
        workoutDao = database.workoutDao
    }

    @Test
    fun insertAndLoadWorkouts() = runBlocking {
        //Start with list of workouts
        val workout = TestUtil.createWorkout(id = 100, isSaved = false)
        val listToBeInserted = listOf(workout)

        //Insert the list to db
        workoutDao.insertAll(listToBeInserted)

        //Get flow data from db and verify it matches the inserted list.
        val job = launch {
            workoutDao.getWorkouts().flowOn(Dispatchers.Default)
                .collect { result ->
                    assertThat(result, `is`(listToBeInserted))
                }
        }
        job.cancel()
    }

    @Test
    fun insertAndLoadById() = runBlocking {
        //Start with list of workouts
        val workout = TestUtil.createWorkout(id = 100, isSaved = false)
        val listToBeInserted = listOf(workout)

        //Insert the list to db
        workoutDao.insertAll(listToBeInserted)

        //Get workout by id from db
        val loaded = workoutDao.getWorkout(100).getOrAwaitValue()

        //Verify that data loaded matches inserted workout
        assertThat(loaded.id, `is`(workout.id))
        assertThat(loaded.title, `is`(workout.title))
        assertThat(loaded.category, `is`(workout.category))
        assertThat(loaded.isSaved, `is`(workout.isSaved))
    }

    @Test
    fun updateWorkout() = runBlocking {
        //Start with list of workouts
        val workout = TestUtil.createWorkout(id = 100, isSaved = false)
        val listToBeInserted = listOf(workout)

        //Insert the list to db
        workoutDao.insertAll(listToBeInserted)

        //Get workout by id and assert the value of isSaved is false
        var loaded = workoutDao.getWorkout(100).getOrAwaitValue()
        assertThat(loaded.isSaved, `is`(false))

        //Update the workout
        workout.isSaved = true
        workoutDao.update(workout)

        //Get workout by id from db and assert the value of isSaved is now true.
        loaded = workoutDao.getWorkout(100).getOrAwaitValue()
        assertThat(loaded.isSaved, `is`(true))
    }
}