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
import java.time.LocalDate

class RegistroDiarioViewModel(
    private val prendaRepository: PrendaRepository,
    private val outfitRepository: OutfitRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    val prendasDisponibles: StateFlow<List<PrendaEntity>> = dataStoreManager.getUserId
        .flatMapLatest { userId ->
            if (userId != null) prendaRepository.obtenerPrendasPorUsuario(userId)
            else flowOf(emptyList())
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _prendasSeleccionadas = MutableStateFlow<Set<Int>>(emptySet())
    val prendasSeleccionadas: StateFlow<Set<Int>> = _prendasSeleccionadas.asStateFlow()

    fun togglePrenda(id: Int) {
        val actuales = _prendasSeleccionadas.value
        _prendasSeleccionadas.value = if (actuales.contains(id)) actuales - id else actuales + id
    }

    fun toggleFavorito(prenda: PrendaEntity) {
        viewModelScope.launch {
            prendaRepository.actualizarFavorito(prenda.id, !prenda.favorito)
        }
    }

    fun guardarRegistroDiario(onSuccess: () -> Unit) {
        val seleccionadas = _prendasSeleccionadas.value.toList()
        if (seleccionadas.isEmpty()) return

        viewModelScope.launch {
            val userId = dataStoreManager.getUserId.first() ?: return@launch
            val fechaHoy = LocalDate.now().toString()

            val registro = OutfitEntity(
                idUsuario = userId,
                nombre = "Registro Diario",
                estilo = "Diario",
                temporada = "Actual",
                tags = listOf("Diario"),
                fecha = fechaHoy
            )

            outfitRepository.guardarOutfitConPrendas(registro, seleccionadas)
            onSuccess()
        }
    }

    class Factory(
        private val prendaRepo: PrendaRepository,
        private val outfitRepo: OutfitRepository,
        private val dataStore: DataStoreManager
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            RegistroDiarioViewModel(prendaRepo, outfitRepo, dataStore) as T
    }
}