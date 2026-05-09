package ramirez.ruben.closetvirtual.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ramirez.ruben.closetvirtual.data.database.repository.UsuarioRepository
import ramirez.ruben.closetvirtual.data.datastore.DataStoreManager

class LoginViewModel(private val repository: UsuarioRepository, private val dataStoreManager: DataStoreManager) : ViewModel() {

    fun login(correo: String, password: String, onLoginSuccess: () -> Unit, onLoginError: (String) -> Unit) {
        if (correo.isBlank() || password.isBlank()) {
            onLoginError("Por favor, complete todos los campos")
            return
        }

        viewModelScope.launch {
            val user = repository.login(correo, password)
            if (user != null) {
                dataStoreManager.saveUserId(user.id)
                onLoginSuccess()
            } else {
                onLoginError("Correo o contraseña incorrectos")
            }
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
