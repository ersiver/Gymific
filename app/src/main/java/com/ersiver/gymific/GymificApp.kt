package com.ersiver.gymific

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.ersiver.gymific.worker.PopulateCategoryTableWorker
import com.ersiver.gymific.worker.PopulateWorkoutTableWorker
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class GymificApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()
        plantTimber()
        prepopulateDatabase()
    }

    private fun plantTimber() {
        val applicationScope = CoroutineScope(Dispatchers.Default)
        applicationScope.launch {
            Timber.plant(Timber.DebugTree())
        }
    }

    //Pre-populate database with raw data.
    private fun prepopulateDatabase() {
        val populateWorkoutTable = OneTimeWorkRequestBuilder<PopulateWorkoutTableWorker>().build()
        val populateCategoryTable = OneTimeWorkRequestBuilder<PopulateCategoryTableWorker>().build()

            WorkManager.getInstance(applicationContext)
            .beginWith(populateWorkoutTable)
            .then(populateCategoryTable)
            .enqueue()
    }

    //To inject WorkManager object by Hilt.
    override fun getWorkManagerConfiguration() =
        Configuration.Builder().setWorkerFactory(workerFactory).build()
}