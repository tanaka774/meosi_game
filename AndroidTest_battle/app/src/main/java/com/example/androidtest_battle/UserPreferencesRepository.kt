package com.example.androidtest_battle

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

//private val Context.dataStore by preferencesDataStore(
//    name = USER_PREFERENCES_NAME
//)

private object PreferencesKeys {
    val SUM_COUNT = intPreferencesKey("sum_count")
}

data class UserPreferences(val dsSumCount: Int)


class UserPreferencesRepository(private val dataStore: DataStore<Preferences>) {
    val userPreferencesFlow: Flow<UserPreferences> = dataStore.data.map { preferences ->
        val dsSumCount = preferences[PreferencesKeys.SUM_COUNT] ?: 0
        UserPreferences(dsSumCount)
    }

    suspend fun updateDsSumCount(dsSumCount: Int) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.SUM_COUNT] = dsSumCount
        }
    }
}