package ramirez.ruben.closetvirtual.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ramirez.ruben.closetvirtual.data.database.entity.UsuarioEntity
import ramirez.ruben.closetvirtual.data.database.repository.UsuarioRepository
import ramirez.ruben.closetvirtual.data.datastore.DataStoreManager
import java.text.SimpleDateFormat
import java.util.Locale

class RegisterViewModel(private val repository: UsuarioRepository, private val dataStoreManager: DataStoreManager) : ViewModel() {

    fun registrarUsuario(nombre: String, correo: String, password: String, fechaNacimiento: String, genero: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        if (nombre.isBlank() || correo.isBlank() || password.isBlank() || fechaNacimiento.isBlank() || genero.isBlank()) {
            onError("Todos los campos son obligatorios")
            return
        }

        val nacimientoLong = try {
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            sdf.parse(fechaNacimiento)?.time ?: 0L
        } catch (e: Exception) {
            0L
        }

        if (nacimientoLong == 0L) {
            onError("Fecha de nacimiento inválida (DD/MM/AAAA)")
            return
        }

        viewModelScope.launch {
            try {
                val user = UsuarioEntity(
                    nombre = nombre,
                    correo = correo,
                    password = password,
                    nacimiento = nacimientoLong,
                    genero = genero
                )
                val newUserId = repository.registrarUsuario(user)
                dataStoreManager.saveUserId(newUserId.toInt())
                onSuccess()
            } catch (e: Exception) {
                onError("Error al registrar: ${e.message}")
            }
        }
    }

    class Factory(private val repository: UsuarioRepository, private val dataStoreManager: DataStoreManager) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return RegisterViewModel(repository, dataStoreManager) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
