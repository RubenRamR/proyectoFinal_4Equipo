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
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

class GestionPrendaViewModel(
    private val repository: PrendaRepository,
    private val context: Context
) : ViewModel() {

    private val _prendaCargada = MutableStateFlow<PrendaEntity?>(null)
    val prendaCargada: StateFlow<PrendaEntity?> = _prendaCargada.asStateFlow()

    fun cargarPrendaParaEdicion(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _prendaCargada.value = repository.obtenerPrendaPorId(id)
        }
    }

    fun guardarOActualizarPrenda(
        idExistente: String?, nombre: String, marca: String, uriImagenTemporal: Uri?,
        rutaImagenAnterior: String?, categoria: String, color: String,
        esEstampada: Boolean, talla: String, temporada: String,
        formalidad: String, tags: List<String>
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val rutaLocal = if (uriImagenTemporal != null) {
                copiarImagenAlAlmacenamientoInterno(uriImagenTemporal)
            } else {
                rutaImagenAnterior ?: ""
            }

            val prenda = PrendaEntity(
                id = idExistente ?: UUID.randomUUID().toString(),
                nombre = nombre,
                marca = marca,
                imagenUri = rutaLocal,
                categoria = categoria,
                color = color,
                esEstampada = esEstampada,
                talla = talla,
                temporada = temporada,
                formalidad = formalidad,
                tags = tags
            )

            if (idExistente != null) {
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

    private suspend fun copiarImagenAlAlmacenamientoInterno(uri: Uri): String {
        return withContext(Dispatchers.IO) {
            try {
                val nombreArchivo = "prenda_${System.currentTimeMillis()}.jpg"
                val archivoDestino = File(context.filesDir, nombreArchivo)

                context.contentResolver.openInputStream(uri)?.use { inputStream ->
                    FileOutputStream(archivoDestino).use { outputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }
                archivoDestino.absolutePath
            } catch (e: Exception) {
                e.printStackTrace()
                ""
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