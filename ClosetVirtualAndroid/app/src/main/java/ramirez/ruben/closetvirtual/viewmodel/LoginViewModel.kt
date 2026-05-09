package ramirez.ruben.closetvirtual.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ramirez.ruben.closetvirtual.data.database.repository.UsuarioRepository

class LoginViewModel(private val repository: UsuarioRepository) : ViewModel() {

    fun login(correo: String, password: String, onLoginSuccess: () -> Unit, onLoginError: (String) -> Unit) {
        if (correo.isBlank() || password.isBlank()) {
            onLoginError("Por favor, complete todos los campos")
            return
        }

        viewModelScope.launch {
            val user = repository.login(correo, password)
            if (user != null) {
                onLoginSuccess()
            } else {
                onLoginError("Correo o contraseña incorrectos")
            }
        }
    }

    class Factory(private val repository: UsuarioRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return LoginViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
