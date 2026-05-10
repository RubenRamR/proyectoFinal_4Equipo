package ramirez.ruben.closetvirtual.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ramirez.ruben.closetvirtual.data.database.repository.UsuarioRepository
import ramirez.ruben.closetvirtual.data.datastore.DataStoreManager

class LoginViewModel(private val repository: UsuarioRepository, private val dataStoreManager: DataStoreManager) : ViewModel() {

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    val isBiometricsEnabled: Flow<Boolean> = dataStoreManager.isBiometricsEnabled
    val userId: Flow<Int?> = dataStoreManager.getUserId

    fun login(correo: String, password: String, onLoginSuccess: () -> Unit, onLoginError: (String) -> Unit) {
        if (correo.isBlank() || password.isBlank()) {
            _errorMessage.value = "Por favor, complete todos los campos"
            onLoginError("Por favor, complete todos los campos")
            return
        }

        viewModelScope.launch {
            val user = repository.login(correo, password)
            if (user != null) {
                println("Debug: Usuario encontrado: ${user.id}")
                _errorMessage.value = null
                dataStoreManager.saveUserId(user.id)
                dataStoreManager.setBiometricsEnabled(user.isBiometricsEnabled)
                println("Debug: Sesión guardada en DataStore para ID: ${user.id}")
                onLoginSuccess()
            } else {
                println("Debug: Credenciales incorrectas para: $correo")
                _errorMessage.value = "Correo o contraseña incorrectos"
                onLoginError("Correo o contraseña incorrectos")
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }

    fun loginWithBiometrics(onLoginSuccess: () -> Unit) {
        viewModelScope.launch {
            dataStoreManager.loginWithBiometrics()
            onLoginSuccess()
        }
    }

    fun toggleBiometrics(enabled: Boolean) {
        viewModelScope.launch {
            dataStoreManager.setBiometricsEnabled(enabled)
        }
    }

    fun logout() {
        viewModelScope.launch {
            val isBioEnabled = isBiometricsEnabled.first()
            dataStoreManager.logout(keepUser = isBioEnabled)
        }
    }

    class Factory(private val repository: UsuarioRepository, private val dataStoreManager: DataStoreManager) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return LoginViewModel(repository, dataStoreManager) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
