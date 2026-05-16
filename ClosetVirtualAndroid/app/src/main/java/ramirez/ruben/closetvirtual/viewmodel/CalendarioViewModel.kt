package ramirez.ruben.closetvirtual.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import ramirez.ruben.closetvirtual.data.database.repository.OutfitRepository
import ramirez.ruben.closetvirtual.data.datastore.DataStoreManager
import java.time.LocalDate

class CalendarioViewModel(
    private val outfitRepository: OutfitRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate: StateFlow<LocalDate> = _selectedDate.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val outfitsDelDia: StateFlow<List<OutfitConPrendas>> = combine(
        dataStoreManager.getUserId,
        _selectedDate
    ) { userId, date -> Pair(userId, date.toString()) }

        .flatMapLatest { (userId, fechaStr) ->
            if (userId != null) {

                _selectedDate.flatMapLatest { date ->
                    val fechaString = date.toString()

                    // Al retornar el Flow del repositorio, Room notificará automáticamente el cambio
                    outfitRepository.obtenerOutfitsPorFecha(userId, fechaString).flatMapLatest { list ->
                        if (list.isEmpty()) {
                            flowOf(emptyList())
                        } else {
                            val flows = list.map { outfit ->
                                outfitRepository.obtenerPrendasDeOutfit(outfit.id).map { prendas ->
                                    OutfitConPrendas(outfit, prendas)
                                }
                            }
                            combine(flows) { it.toList() }
                        }
                    }
                }
            } else {
                flowOf(emptyList())
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun updateSelectedDate(date: LocalDate) {
        _selectedDate.value = date
    }

    class Factory(
        private val outfitRepo: OutfitRepository,
        private val dataStore: DataStoreManager
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            CalendarioViewModel(outfitRepo, dataStore) as T
    }
}