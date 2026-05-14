package ramirez.ruben.closetvirtual.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import ramirez.ruben.closetvirtual.data.database.entity.PrendaEntity
import ramirez.ruben.closetvirtual.data.database.repository.OutfitRepository

class DetalleOutfitViewModel(
    outfitRepository: OutfitRepository,
    outfitId: Int
) : ViewModel() {

    val prendas: StateFlow<List<PrendaEntity>> = outfitRepository.obtenerPrendasDeOutfit(outfitId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    class Factory(
        private val outfitRepository: OutfitRepository,
        private val outfitId: Int
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DetalleOutfitViewModel(outfitRepository, outfitId) as T
        }
    }
}
