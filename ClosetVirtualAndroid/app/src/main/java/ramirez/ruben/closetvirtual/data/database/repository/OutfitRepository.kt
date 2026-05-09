package ramirez.ruben.closetvirtual.data.database.repository

import kotlinx.coroutines.flow.Flow
import ramirez.ruben.closetvirtual.data.database.dao.OutfitDao
import ramirez.ruben.closetvirtual.data.database.entity.OutfitEntity
import ramirez.ruben.closetvirtual.data.database.entity.PrendaEntity

class OutfitRepository(private val outfitDao: OutfitDao) {

    suspend fun guardarOutfitConPrendas(outfit: OutfitEntity, prendasIds: List<Int>) {
        outfitDao.insertarOutfitConPrendas(outfit, prendasIds)
    }

    fun obtenerOutfitsPorUsuario(idUsuario: Int): Flow<List<OutfitEntity>> {
        return outfitDao.obtenerOutfitsPorUsuario(idUsuario)
    }

    fun obtenerPrendasDeOutfit(idOutfit: Int): Flow<List<PrendaEntity>> {
        return outfitDao.obtenerPrendasDeOutfit(idOutfit)
    }
}
