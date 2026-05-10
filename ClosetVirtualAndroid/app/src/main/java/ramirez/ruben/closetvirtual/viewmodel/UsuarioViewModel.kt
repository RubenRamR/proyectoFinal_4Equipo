package ramirez.ruben.closetvirtual.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ramirez.ruben.closetvirtual.data.database.entity.UsuarioEntity
import ramirez.ruben.closetvirtual.data.database.repository.UsuarioRepository

class UsuarioViewModel(
    private val repository: UsuarioRepository
) : ViewModel() {

    // sesión del usuario actual
    private val _usuarioActual = MutableStateFlow<UsuarioEntity?>(null)
    val usuarioActual: StateFlow<UsuarioEntity?> = _usuarioActual.asStateFlow()

    // Manejo de errores
    private val _uiStateMessage = MutableStateFlow<String?>(null)
    val uiStateMessage: StateFlow<String?> = _uiStateMessage.asStateFlow()

    fun registrar(
        nombre: String,
        correo: String,
        contrasena: String,
        fechaNacimiento: String,
        genero: String,
        onSuccess: (Int) -> Unit
    ) {

        viewModelScope.launch(Dispatchers.IO) {
            if (repository.existeCorreo(correo)) {
                _uiStateMessage.value = "El correo ya está registrado"
                return@launch
            }

            val nuevoUsuario = UsuarioEntity(
                nombre = nombre,
                correo = correo,
                contrasena = contrasena,
                fechaNacimiento = fechaNacimiento,
                genero = genero
            )
            val id = repository.registrarUsuario(nuevoUsuario)
            val usuarioConId = nuevoUsuario.copy(id = id.toInt())
            _usuarioActual.value = usuarioConId
            viewModelScope.launch(Dispatchers.Main) { onSuccess(id.toInt()) }
        }
    }

    fun login(
        correo: String,
        contrasena: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val usuario = repository.login(correo, contrasena)
            if (usuario != null) {
                _usuarioActual.value = usuario
                viewModelScope.launch(Dispatchers.Main) { onSuccess() }
            } else {
                _uiStateMessage.value = "Credenciales incorrectas"
            }
        }
    }

    fun actualizarPerfil(usuarioActualizado: UsuarioEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.actualizarUsuario(usuarioActualizado)
            _usuarioActual.value = usuarioActualizado
        }
    }

    fun logout() {
        _usuarioActual.value = null
    }

    fun clearMessage() {
        _uiStateMessage.value = null
    }

    fun recuperarContrasena(correo: String) {
        viewModelScope.launch(Dispatchers.IO) {
            // Verificamos si el correo existe en nuestra base de datos local
            if (repository.existeCorreo(correo)) {
                _uiStateMessage.value = "Se han enviado las instrucciones a tu correo."
            } else {
                _uiStateMessage.value = "No existe ninguna cuenta con este correo."
            }
        }
    }

    class Factory(
        private val repository: UsuarioRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UsuarioViewModel::class.java)) {
                return UsuarioViewModel(repository) as T
            }
            throw IllegalArgumentException("error en el ViewModel")
        }
    }
}