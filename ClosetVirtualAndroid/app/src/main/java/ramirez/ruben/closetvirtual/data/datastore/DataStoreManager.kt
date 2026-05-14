package ramirez.ruben.closetvirtual.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings_prefs")

class DataStoreManager(private val context: Context) {
    companion object {
        val USER_ID_KEY = intPreferencesKey("user_id")
        val IS_LOGGED_IN_KEY = booleanPreferencesKey("is_logged_in")
        val BIOMETRICS_ENABLED_KEY = booleanPreferencesKey("biometrics_enabled")
        val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
    }

    val getUserId: Flow<Int?> = context.dataStore.data
        .map { preferences ->
            preferences[USER_ID_KEY]
        }

    val isLoggedIn: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[IS_LOGGED_IN_KEY] ?: false
        }

    val isBiometricsEnabled: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[BIOMETRICS_ENABLED_KEY] ?: false
        }

    val isDarkThemeEnabled: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[DARK_MODE_KEY] ?: false
        }

    suspend fun saveUserId(id: Int) {
        context.dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = id
            preferences[IS_LOGGED_IN_KEY] = true
        }
    }

    suspend fun setBiometricsEnabled(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[BIOMETRICS_ENABLED_KEY] = enabled
        }
    }

    suspend fun setDarkThemeEnabled(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[DARK_MODE_KEY] = enabled
        }
    }

    suspend fun loginWithBiometrics() {
        context.dataStore.edit { preferences ->
            preferences[IS_LOGGED_IN_KEY] = true
        }
    }

    suspend fun clearUserId() {
        context.dataStore.edit { preferences ->
            preferences.remove(USER_ID_KEY)
            preferences[IS_LOGGED_IN_KEY] = false
        }
    }

    suspend fun logout(keepUser: Boolean = false) {
        context.dataStore.edit { preferences ->
            if (keepUser) {
                preferences[IS_LOGGED_IN_KEY] = false
            } else {
                preferences.clear()
            }
        }
    }
}
