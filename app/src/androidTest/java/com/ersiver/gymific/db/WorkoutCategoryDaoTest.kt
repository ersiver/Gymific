package com.ersiver.gymific.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.ersiver.gymific.util.TestUtil
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class WorkoutCategoryDaoTest : LocalDatabase() {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var workoutCategoryDao: WorkoutCategoryDao

    @Before
    fun init() {
        workoutCategoryDao = database.categoryDao
    }

    @Test
    fun insertAndLoad() = runBlocking {
        //Start with the list of categories
        val workoutCategory = TestUtil.createWorkoutCategory()
        val listToBeInserted = listOf(workoutCategory)

        //Insert the list to database
        workoutCategoryDao.insertAll(listToBeInserted)

        //Get data from db and verify it matches inserted list.
        val job = launch{
            workoutCategoryDao.getCategories().flowOn(Dispatchers.Default)
                .collect{result->
                    assertThat(result, `is`(listToBeInserted))
                }
        }
        job.cancel()
    }
}