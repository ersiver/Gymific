package com.ersiver.gymific.worker

import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.work.ListenableWorker
import androidx.work.testing.TestListenableWorkerBuilder
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@SmallTest
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class PopulateCategoryTableWorkerTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    private lateinit var context: Context

    @Before
    fun setUp() {
        hiltRule.inject()

        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun testPopulateCategoryTable() {
        val worker =
            TestListenableWorkerBuilder<PopulateCategoryTableWorker>(context).setWorkerFactory(
                workerFactory
            ).build()

        val result = worker.startWork().get()
        assertThat(result, `is`(ListenableWorker.Result.success()))
    }
}