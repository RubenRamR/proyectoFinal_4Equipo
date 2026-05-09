package ramirez.ruben.closetvirtual.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ramirez.ruben.closetvirtual.data.database.entity.OutfitEntity
import ramirez.ruben.closetvirtual.data.database.entity.PrendaEntity
import ramirez.ruben.closetvirtual.data.database.repository.OutfitRepository
import ramirez.ruben.closetvirtual.data.database.repository.PrendaRepository
import ramirez.ruben.closetvirtual.data.datastore.DataStoreManager

class AgregarOutfitViewModel(
    private val prendaRepository: PrendaRepository,
    private val outfitRepository: OutfitRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _prendasDisponibles = MutableStateFlow<List<PrendaEntity>>(emptyList())
    val prendasDisponibles: StateFlow<List<PrendaEntity>> = _prendasDisponibles.asStateFlow()

    private val _prendasSeleccionadas = MutableStateFlow<Set<Int>>(emptySet())
    val prendasSeleccionadas: StateFlow<Set<Int>> = _prendasSeleccionadas.asStateFlow()

    private val _mensajeError = MutableStateFlow<String?>(null)
    val mensajeError: StateFlow<String?> = _mensajeError.asStateFlow()

    init {
        cargarPrendasUsuario()
    }

    private fun cargarPrendasUsuario() {
        viewModelScope.launch {
            dataStoreManager.getUserId.collect { id ->
                id?.let {
                    prendaRepository.obtenerPrendasPorUsuario(it).collect { list ->
                        _prendasDisponibles.value = list
                    }
                }
            }
        }
    }

    fun toggleSeleccionPrenda(prendaId: Int) {
        val current = _prendasSeleccionadas.value
        if (current.contains(prendaId)) {
            _prendasSeleccionadas.value = current - prendaId
            _mensajeError.value = null
        } else {
            if (current.size < 5) {
                _prendasSeleccionadas.value = current + prendaId
                _mensajeError.value = null
            } else {
                _mensajeError.value = "Solo puedes seleccionar un máximo de 5 prendas"
            }
        }
    }

    fun guardarOutfit(nombre: String, estilo: String, temporada: String, tags: List<String>, onSuccess: () -> Unit) {
        if (nombre.isBlank() || estilo.isBlank() || temporada.isBlank()) {
            _mensajeError.value = "Por favor completa los campos obligatorios"
            return
        }

        if (_prendasSeleccionadas.value.isEmpty()) {
            _mensajeError.value = "Debes seleccionar al menos una prenda"
            return
        }

        viewModelScope.launch {
            val userId = dataStoreManager.getUserId.first()
            if (userId != null) {
                val outfit = OutfitEntity(
                    idUsuario = userId,
                    nombre = nombre,
                    estilo = estilo,
                    temporada = temporada,
                    tags = tags
                )
                outfitRepository.guardarOutfitConPrendas(outfit, _prendasSeleccionadas.value.toList())
                onSuccess()
            }
        }
    }

    fun limpiarError() {
        _mensajeError.value = null
    }

    class Factory(
        private val prendaRepository: PrendaRepository,
        private val outfitRepository: OutfitRepository,
        private val dataStoreManager: DataStoreManager
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AgregarOutfitViewModel(prendaRepository, outfitRepository, dataStoreManager) as T
        }
    }
}
