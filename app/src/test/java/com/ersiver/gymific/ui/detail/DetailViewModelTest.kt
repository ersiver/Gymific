package com.ersiver.gymific.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.ersiver.gymific.model.Workout
import com.ersiver.gymific.repository.UserPreferenceRepository
import com.ersiver.gymific.repository.WorkoutRepository
import com.ersiver.gymific.util.MainCoroutinesRule
import com.ersiver.gymific.util.TestUtil
import com.ersiver.gymific.util.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class DetailViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutinesRule = MainCoroutinesRule()

    private lateinit var viewModel: DetailViewModel
    private val repository = mock(WorkoutRepository::class.java)
    private val userPreferenceRepository = mock(UserPreferenceRepository::class.java)
    private val workout = TestUtil.createWorkout(id = 1, isSaved = false)

    @Before
    fun setUp() {
        viewModel = DetailViewModel(repository, userPreferenceRepository)

        //DetailViewModel always starts with the start() function.
        val liveDataWorkout = MutableLiveData<Workout>().apply {
            value = workout
        }
        `when`(repository.getWorkout(1)).thenReturn(liveDataWorkout)
        viewModel.start(1)
    }

    /**
     * Test to check, that when the function start(workoutId: Int) is
     * invoked, then loading of workout from repository is triggered.
     */
    @Test
    fun start_getWorkout() = runBlockingTest {
        val workoutValue = viewModel.workout.getOrAwaitValue()
        verify(repository, atLeastOnce()).getWorkout(1)
        assertThat(workoutValue.id, `is`(1))
        assertThat(workoutValue.title, `is`(workout.title))
        assertThat(workoutValue.category, `is`(workout.category))
        assertThat(workoutValue.instruction, `is`(workout.instruction))
        assertThat(workoutValue.time, `is`(workout.time))
        assertThat(workoutValue.isSaved, `is`(false))
    }

    /**
     * Test to check, that when the function start(workoutId: Int) is invoked and workout
     * is loaded, then transformation of workout is triggered and the workoutTime is set.
     */
    @Test
    fun start_getWorkoutTime()= runBlockingTest {
        val timeValue = viewModel.workoutTimeMillis.getOrAwaitValue()
        assertThat(timeValue, `is`(workout.time * 1000))
    }

    /**
     * Test to check, that when the function start(workoutId: Int) is invoked,
     * then loading of pausedTime from userPreferenceRepository is triggered.
     */
    @Test
    fun start_getPausedTime() = runBlockingTest{
        val pausedTimeFlow: Flow<Long> = flowOf(30_000)
        `when`(userPreferenceRepository.getPausedTime(1)).thenReturn(pausedTimeFlow)
        val pausedTime = viewModel.savedPausedTime.getOrAwaitValue()
        verify(userPreferenceRepository, atLeastOnce()).getPausedTime(1)
        assertThat(pausedTime, `is`(30_000))
    }

    /**
     * Test to check, than when the setFavourite is called, then isSaved value is changed.
     */
    @Test
    fun setFavourite_changeIsSavedValue()= runBlockingTest {
        val workoutValue = viewModel.workout.getOrAwaitValue()
        assertThat(workoutValue.isSaved, `is`(false))
        viewModel.setFavourite(workout)
        assertThat(workoutValue.isSaved, `is`(true))
    }
}