package com.ersiver.gymific.repository

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import com.ersiver.gymific.util.PREFERENCE_NAME
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

enum class SortOrder {
    BY_DATE,
    BY_TITLE,
    BY_TIME,
    BY_CATEGORY
}

data class UserPreferences(val sortOrder: SortOrder)

private const val SORT_KEY = "sort_order"

class UserPreferenceRepository @Inject constructor(@ActivityContext context: Context) {
    private val dataStore: DataStore<Preferences> = context.createDataStore(name = PREFERENCE_NAME)

    /**
     * Save a new sort order to DataStore. prefKey is always constant.
     */
    suspend fun setSortOrder(order: String) {
        dataStore.edit { preferences ->
            val newSortOrder = SortOrder.valueOf(order)
            preferences[preferencesKey<String>(SORT_KEY)] = newSortOrder.name
        }
    }

    /**
     * Return sort preferences.
     */
    val userPreferencesFlow: Flow<UserPreferences> = dataStore.data
        .catch {
            emit(emptyPreferences())
        }
        .map { preferences ->
            //Set default order to BY_DATE
            val sortAsString =
                preferences[preferencesKey<String>(SORT_KEY)] ?: SortOrder.BY_DATE.name
            val sortOrder = SortOrder.valueOf(sortAsString)
            UserPreferences(sortOrder)
        }

    /**
     * Save a pausedTime to DataStore.
     */
    suspend fun savePausedTime(prefKey: Int, timeMillis: Long) {
        dataStore.edit { preferences ->
            preferences[preferencesKey<Long>(prefKey.toString())] = timeMillis
        }
    }

    /**
     * Return workout paused time. The prefKey matches the workout's id.
     */
    fun getPausedTime(prefKey: Int): Flow<Long> = dataStore.data
        .catch {
            emit(emptyPreferences())
        }
        .map { preferences ->
            preferences[preferencesKey(prefKey.toString())] ?: 0
        }
}