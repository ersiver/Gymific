package com.ersiver.gymific.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ersiver.gymific.repository.WorkoutRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class HomeViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: HomeViewModel
    private lateinit var repository: WorkoutRepository

    @Before
    fun setUp() {
        repository = mock(WorkoutRepository::class.java)
        viewModel = HomeViewModel(repository)
    }

    @Test
    fun start_getRecommendedWorkouts() {
        verify(repository, atLeastOnce()).getWorkouts()
    }

    @Test
    fun start_getCategories(){
        verify(repository, atLeastOnce()).getCategories()
    }
}