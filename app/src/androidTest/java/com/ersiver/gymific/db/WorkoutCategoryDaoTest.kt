package com.ersiver.gymific.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.ersiver.gymific.model.WorkoutCategory
import com.ersiver.gymific.util.TestUtil
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
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
        val categories: List<WorkoutCategory> = listOf(workoutCategory)

        //Insert the list to database
        workoutCategoryDao.insertAll(categories)

        //Get data from db and verify it matches inserted list.
        val categoriesFlow: Flow<List<WorkoutCategory>> = workoutCategoryDao.getCategories()
        val loadedCategories = categoriesFlow.first()

        assertThat(loadedCategories, `is`(categories))
        assertThat(loadedCategories[0], `is`(categories[0]))
        assertThat(loadedCategories[0].id, `is`(categories[0].id))
        assertThat(loadedCategories[0].title, `is`(categories[0].title))
        assertThat(loadedCategories[0].description, `is`(categories[0].description))
        assertThat(loadedCategories[0].overview, `is`(categories[0].overview))
    }
}