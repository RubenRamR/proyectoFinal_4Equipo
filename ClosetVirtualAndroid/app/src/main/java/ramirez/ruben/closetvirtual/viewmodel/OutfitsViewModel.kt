package ramirez.ruben.closetvirtual.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import ramirez.ruben.closetvirtual.data.database.entity.OutfitEntity
import ramirez.ruben.closetvirtual.data.database.entity.PrendaEntity
import ramirez.ruben.closetvirtual.data.database.repository.OutfitRepository
import ramirez.ruben.closetvirtual.data.datastore.DataStoreManager

data class OutfitConPrendas(
    val outfit: OutfitEntity,
    val prendas: List<PrendaEntity>
)

class OutfitsViewModel(
    private val outfitRepository: OutfitRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val outfits: StateFlow<List<OutfitConPrendas>> = dataStoreManager.getUserId
        .flatMapLatest { userId ->
            if (userId != null) {
                outfitRepository.obtenerOutfitsPorUsuario(userId).flatMapLatest { list ->
                    if (list.isEmpty()) {
                        flowOf(emptyList())
                    } else {
                        // Para cada outfit, obtenemos sus prendas
                        val flows = list.map { outfit ->
                            outfitRepository.obtenerPrendasDeOutfit(outfit.id).map { prendas ->
                                OutfitConPrendas(outfit, prendas)
                            }
                        }
                        combine(flows) { it.toList() }
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

    class Factory(
        private val outfitRepository: OutfitRepository,
        private val dataStoreManager: DataStoreManager
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return OutfitsViewModel(outfitRepository, dataStoreManager) as T
        }
    }
}
