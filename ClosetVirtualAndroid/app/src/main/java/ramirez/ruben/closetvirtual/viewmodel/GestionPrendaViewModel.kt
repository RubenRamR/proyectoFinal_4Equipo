package ramirez.ruben.closetvirtual.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ramirez.ruben.closetvirtual.data.database.entity.PrendaEntity
import ramirez.ruben.closetvirtual.data.database.repository.PrendaRepository
import java.io.ByteArrayOutputStream
import java.io.InputStream

class GestionPrendaViewModel(
    private val repository: PrendaRepository,
    private val context: Context
) : ViewModel() {

    private val _prendaCargada = MutableStateFlow<PrendaEntity?>(null)
    val prendaCargada: StateFlow<PrendaEntity?> = _prendaCargada.asStateFlow()

    fun cargarPrendaParaEdicion(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _prendaCargada.value = repository.obtenerPrendaPorId(id)
        }
    }

    fun guardarOActualizarPrenda(
        idExistente: Int?, idUsuario: Int, nombre: String, marca: String, uriImagenTemporal: Uri?,
        imagenActual: ByteArray?, categoria: String, color: String,
        esEstampada: Boolean, talla: String, temporada: String,
        formalidad: String, tags: List<String>
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val imagenBytes = if (uriImagenTemporal != null) {
                convertirUriAByteArray(uriImagenTemporal)
            } else {
                imagenActual
            }

            val prenda = PrendaEntity(
                id = idExistente ?: 0,
                idUsuario = idUsuario,
                nombre = nombre,
                marca = marca,
                imagen = imagenBytes,
                categoria = categoria,
                color = color,
                estampada = esEstampada,
                talla = talla,
                temporada = temporada,
                formalidad = formalidad,
                tags = tags
            )

            if (idExistente != null && idExistente != 0) {
                repository.actualizarPrenda(prenda)
            } else {
                repository.insertarPrenda(prenda)
            }
        }
    }

    fun eliminarPrendaCargada() {
        val prenda = _prendaCargada.value ?: return
        viewModelScope.launch(Dispatchers.IO) {
            repository.eliminarPrenda(prenda)
        }
    }

    private suspend fun convertirUriAByteArray(uri: Uri): ByteArray? {
        return withContext(Dispatchers.IO) {
            try {
                context.contentResolver.openInputStream(uri)?.use { inputStream ->
                    val byteBuffer = ByteArrayOutputStream()
                    val bufferSize = 1024
                    val buffer = ByteArray(bufferSize)
                    var len: Int
                    while (inputStream.read(buffer).also { len = it } != -1) {
                        byteBuffer.write(buffer, 0, len)
                    }
                    byteBuffer.toByteArray()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    class Factory(
        private val repository: PrendaRepository,
        private val context: Context
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(GestionPrendaViewModel::class.java)) {
                return GestionPrendaViewModel(repository, context) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}