package com.ersiver.gymific.ui.arms

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ersiver.gymific.repository.WorkoutRepository
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

class ArmsViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: ArmsViewModel
    private val repository = mock(WorkoutRepository::class.java)

    @Before
    fun setUp() {
        viewModel = ArmsViewModel(repository)
    }

    @Test
    fun start_getArmsWorkouts() {
        verify(repository, atLeastOnce()).getWorkouts()
    }
}