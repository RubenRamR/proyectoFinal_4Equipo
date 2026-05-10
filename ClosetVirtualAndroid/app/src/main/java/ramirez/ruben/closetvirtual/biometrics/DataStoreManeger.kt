package ramirez.ruben.closetvirtual.biometrics

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager(private val context: Context) {

    private val Context.dataStore by preferencesDataStore("session_preferences")

    companion object {
        val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        val USERNAME = stringPreferencesKey("username")
        val BIOMETRICS_ACTIVE = booleanPreferencesKey("biometrics")
    }

    val isLoggedinFlow: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[IS_LOGGED_IN] ?: false
    }

    val usernameFlow: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[USERNAME] ?: ""
    }

    val biometricActiveFlow: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[BIOMETRICS_ACTIVE] ?: false
    }

    suspend fun saveSession(username: String){
        context.dataStore.edit {
            it[IS_LOGGED_IN] = true
            it[USERNAME] = username
        }
    }

    suspend fun activeBiometrics(active: Boolean){
        context.dataStore.edit {
            it[BIOMETRICS_ACTIVE] = active
        }

    }

    suspend fun logout(keepUser: Boolean = false) {
        context.dataStore.edit {
            if (keepUser) {
                it[IS_LOGGED_IN] = false
            } else {
                it.clear()
            }
        }
    }

    suspend fun loginWithBiometrics(){
        context.dataStore.edit {
            it[IS_LOGGED_IN] = true
        }
    }
}






