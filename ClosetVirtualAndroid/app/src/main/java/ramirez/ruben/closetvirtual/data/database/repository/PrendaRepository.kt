package ramirez.ruben.closetvirtual.data.database.repository

import kotlinx.coroutines.flow.Flow
import ramirez.ruben.closetvirtual.data.database.dao.PrendaDao
import ramirez.ruben.closetvirtual.data.database.entity.PrendaEntity

class PrendaRepository(private val prendaDao: PrendaDao) {

    val todasLasPrendas: Flow<List<PrendaEntity>> = prendaDao.obtenerTodasLasPrendas()

    suspend fun insertarPrenda(prenda: PrendaEntity) {
        prendaDao.insertarPrenda(prenda)
    }

    suspend fun actualizarPrenda(prenda: PrendaEntity) {
        prendaDao.actualizarPrenda(prenda)
    }

    suspend fun eliminarPrenda(prenda: PrendaEntity) {
        prendaDao.eliminarPrenda(prenda)
    }

    suspend fun obtenerPrendaPorId(id: Int): PrendaEntity? {
        return prendaDao.obtenerPrendaPorId(id)
    }
}