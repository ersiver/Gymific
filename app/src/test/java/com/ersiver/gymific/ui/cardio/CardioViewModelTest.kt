package com.ersiver.gymific.ui.cardio

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ersiver.gymific.repository.WorkoutRepository
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

class CardioViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CardioViewModel
    private lateinit var repository: WorkoutRepository

    @Before
    fun setUp() {
        repository = mock(WorkoutRepository::class.java)
        viewModel = CardioViewModel(repository)
    }

    @Test
    fun start_getCardioWorkouts() {
        verify(repository, atLeastOnce()).getWorkouts()
    }
}