package com.ersiver.gymific.worker

import android.content.Context
import androidx.hilt.Assisted
import androidx.hilt.work.WorkerInject
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.ersiver.gymific.R
import com.ersiver.gymific.db.GymificDatabase
import com.ersiver.gymific.model.WorkoutCategory
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import timber.log.Timber

class PopulateCategoryTableWorker @WorkerInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParameters: WorkerParameters,
    private val database: GymificDatabase
) :
    CoroutineWorker(appContext, workerParameters) {

    override suspend fun doWork(): Result {
        return try {
            applicationContext.resources.openRawResource(R.raw.categories).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val type = object : TypeToken<List<WorkoutCategory>>() {}.type
                    val categories: List<WorkoutCategory> = Gson().fromJson(jsonReader, type)
                    database.categoryDao.insertAll(categories)
                }
            }
            Timber.i("Populate category-table request succeed")
            Result.success()
        } catch (e: Exception) {
            Timber.i("Populate category-table failed. ${e.message}")
            Result.failure()
        }
    }
}