package com.ersiver.gymific.ui.legs

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ersiver.gymific.repository.WorkoutRepository
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*


class LegsViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: LegsViewModel
    private lateinit var repository: WorkoutRepository

    @Before
    fun setUp() {
        repository = mock(WorkoutRepository::class.java)
        viewModel = LegsViewModel(repository)
    }

    @Test
    fun start_getLegWorkouts() {
        verify(repository, atLeastOnce()).getWorkouts()
    }
}