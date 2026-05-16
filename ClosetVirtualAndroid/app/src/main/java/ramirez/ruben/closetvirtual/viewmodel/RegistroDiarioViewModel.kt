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

    val streakCount: StateFlow<Int> = dataStoreManager.getStreakCount
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val hasLoggedToday: StateFlow<Boolean> = dataStoreManager.getLastLoggedDate
        .map { it == LocalDate.now().toString() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    init {
        verificarRachaPerdida()
    }

    private fun verificarRachaPerdida() {
        viewModelScope.launch {
            val lastDateStr = dataStoreManager.getLastLoggedDate.first()
            if (lastDateStr != null) {
                val lastDate = LocalDate.parse(lastDateStr)
                val today = LocalDate.now()
                // Si la última fecha es anterior a ayer, se perdió la racha
                if (lastDate.isBefore(today.minusDays(1))) {
                    dataStoreManager.resetStreak()
                }
            }
        }
    }

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
                tags = listOf("Diario")
            )

            outfitRepository.guardarOutfitConPrendas(registro, seleccionadas)

            val currentStreak = dataStoreManager.getStreakCount.first()
            val lastDateStr = dataStoreManager.getLastLoggedDate.first()

            val newStreak = when (lastDateStr) {
                LocalDate.now().minusDays(1).toString() -> currentStreak + 1 // Se registró ayer
                LocalDate.now().toString() -> currentStreak // Ya se registro
                else -> 1 // Racha reiniciada, nooo
            }

            dataStoreManager.updateStreak(newStreak, fechaHoy)
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