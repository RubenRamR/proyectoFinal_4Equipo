package ramirez.ruben.closetvirtual.biometrics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class LoginViewMovel(private val dataStoreManager: DataStoreManager): ViewModel() {
    private val emailCredential = "correo@mail.com"
    private val passwordCredential = "abc1234"

    val isLoggedIn: Flow<Boolean> = dataStoreManager.isLoggedinFlow
    val username: Flow<String> = dataStoreManager.usernameFlow
    val biometricActive: Flow<Boolean> = dataStoreManager.biometricActiveFlow

    fun login(email: String, password: String) {
        if (email.lowercase() == emailCredential && password == passwordCredential) {
            viewModelScope.launch {
                dataStoreManager.saveSession(email)
            }
        }
    }

    fun loginWithBiometrics() {
        viewModelScope.launch {
            dataStoreManager.loginWithBiometrics()
        }
    }

    fun toggleBiometrics(active: Boolean) {
        viewModelScope.launch {
            dataStoreManager.activeBiometrics(active)
        }
    }

    fun logout() {
        viewModelScope.launch {
            val isActive = biometricActive.first()
            dataStoreManager.logout(keepUser = isActive)
        }
    }
}
