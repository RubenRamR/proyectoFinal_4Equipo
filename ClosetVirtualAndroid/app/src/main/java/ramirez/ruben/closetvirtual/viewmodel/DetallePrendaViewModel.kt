package ramirez.ruben.closetvirtual.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ramirez.ruben.closetvirtual.data.database.entity.PrendaEntity
import ramirez.ruben.closetvirtual.data.database.repository.PrendaRepository

class DetallePrendaViewModel(
    private val repository: PrendaRepository
) : ViewModel() {

    private val _prenda = MutableStateFlow<PrendaEntity?>(null)
    val prenda: StateFlow<PrendaEntity?> = _prenda.asStateFlow()

    fun cargarPrenda(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _prenda.value = repository.obtenerPrendaPorId(id)
        }
    }

    fun eliminarPrendaActual(onSuccess: () -> Unit) {
        val prendaActual = _prenda.value ?: return
        viewModelScope.launch(Dispatchers.IO) {
            repository.eliminarPrenda(prendaActual)
            launch(Dispatchers.Main) { onSuccess() }
        }
    }

    class Factory(private val repository: PrendaRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetallePrendaViewModel::class.java)) {
                return DetallePrendaViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}