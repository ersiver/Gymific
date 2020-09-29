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

class UserPreferenceRepository @Inject constructor(@ActivityContext context: Context) {
    private val dataStore: DataStore<Preferences> = context.createDataStore(name = PREFERENCE_NAME)

    private object PreferenceKeys {
        val SORT_ORDER = preferencesKey<String>("sort_order")
    }

    //Return sort preferences.
    val userPreferencesFlow: Flow<UserPreferences> = dataStore.data
        .catch {
            emit(emptyPreferences())
        }
        .map { preferences ->
            //Set default order to BY_DATE
            val sortAsString = preferences[PreferenceKeys.SORT_ORDER] ?: SortOrder.BY_DATE.name
            val sortOrder = SortOrder.valueOf(sortAsString)
            UserPreferences(sortOrder)
        }

    /**
     * Save a new sort order to DataStore.
     */
    suspend fun setSortOrder(order: String) {
        dataStore.edit { preferences ->
            val newSortOrder = SortOrder.valueOf(order)
            preferences[PreferenceKeys.SORT_ORDER] = newSortOrder.name
        }
    }
}