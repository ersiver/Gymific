package com.ersiver.gymific.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.ersiver.gymific.db.WorkoutCategoryDao
import com.ersiver.gymific.db.WorkoutDao
import com.ersiver.gymific.model.Workout
import com.ersiver.gymific.model.WorkoutCategory
import com.ersiver.gymific.util.MainCoroutinesRule
import com.ersiver.gymific.util.TestUtil
import com.ersiver.gymific.util.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class WorkoutRepositoryTest {
    @get:Rule
    val mainCoroutinesRule = MainCoroutinesRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: WorkoutRepository
    private lateinit var workoutDao: WorkoutDao
    private lateinit var workoutCategoryDao: WorkoutCategoryDao
    private lateinit var workout: Workout

    @Before
    fun setUp() {
        workoutDao = mock(WorkoutDao::class.java)
        workoutCategoryDao = mock(WorkoutCategoryDao::class.java)
        repository = WorkoutRepository(workoutDao, workoutCategoryDao)
        workout = TestUtil.createWorkout(1, false)
    }

    @Test
    fun getWorkouts_daoEmitWorkouts() = runBlockingTest {
        //Setup workouts flow.
        val workouts: List<Workout> = listOf(workout)
        val flow: Flow<List<Workout>> = flowOf(workouts)

        //Stub out dao to return a Flow of workouts
        `when`(workoutDao.getWorkouts()).thenReturn(flow)

        //Method under test.
        val workoutsFlow: Flow<List<Workout>> = repository.getWorkouts()

        //Verify that workoutDao was called and data in the result matches emitted workouts.
        verify(workoutDao, atLeastOnce()).getWorkouts()
        workoutsFlow.collect { result ->
            assertThat(result, `is`(workouts))
            assertThat(result.size, `is`(workouts.size))
            assertThat(result[0], `is`(workouts[0]))
        }
    }

    @Test
    fun getWorkoutById_daoLoadWorkout() = runBlockingTest {
        //Setup LiveData workout.
        val data = MutableLiveData<Workout>().apply {
            value = workout
        }

        //Stub out dao to return a Workout LiveData
        `when`(workoutDao.getWorkout(1)).thenReturn(data)

        //Method under test.
        val workoutValue: Workout = repository.getWorkout(1).getOrAwaitValue()

        //Verify that workoutDao was called and data matches loaded workout.
        verify(workoutDao, atLeastOnce()).getWorkout(1)
        assertThat(workoutValue, `is`(workout))
        assertThat(workoutValue.title, `is`(workout.title))
        assertThat(workoutValue.instruction, `is`(workout.instruction))
        assertThat(workoutValue.category, `is`(workout.category))
        assertThat(workoutValue.time, `is`(workout.time))
        assertThat(workoutValue.isSaved, `is`(workout.isSaved))
        assertThat(workoutValue.isRecommended, `is`(workout.isRecommended))
    }

    @Test
    fun updateWorkout_daoUpdateWorkout()= runBlocking {
        //Method under test.
        repository.update(workout)

        //Verify that workoutDao was called to update workout.
        verify(workoutDao, atLeastOnce()).update(workout)
    }

    @Test
    fun getCategories_daoEmitCategories()= runBlockingTest {
        //Setup categories flow.
        val categories: List<WorkoutCategory> = listOf(TestUtil.createWorkoutCategory())
        val flow: Flow<List<WorkoutCategory>> = flowOf(categories)

        //Stub out dao to return a flow.
        `when`(workoutCategoryDao.getCategories()).thenReturn(flow)

        //Method under test.
        val categoriesFlow = repository.getCategories()

        //Verify that categoryDao was called and data in the result matches emitted categories.
        verify(workoutCategoryDao, atLeastOnce()).getCategories()
        categoriesFlow.collect { result->
            assertThat(result, `is`(categories))
            assertThat(result.size, `is`(categories.size))
            assertThat(result[0], `is`(categories[0]))
        }
    }
}