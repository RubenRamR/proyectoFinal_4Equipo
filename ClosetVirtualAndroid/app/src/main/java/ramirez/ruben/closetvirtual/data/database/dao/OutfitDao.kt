package ramirez.ruben.closetvirtual.data.database.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ramirez.ruben.closetvirtual.data.database.entity.OutfitEntity
import ramirez.ruben.closetvirtual.data.database.entity.PrendaOutfitEntity
import ramirez.ruben.closetvirtual.data.database.entity.PrendaEntity

@Dao
interface OutfitDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarOutfit(outfit: OutfitEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarPrendaOutfit(prendaOutfit: PrendaOutfitEntity): Long

    @Query("SELECT * FROM outfit WHERE idUsuario = :idUsuario")
    fun obtenerOutfitsPorUsuario(idUsuario: Int): Flow<List<OutfitEntity>>

    @Query("SELECT * FROM outfit WHERE idUsuario = :idUsuario AND fecha = :fecha")
    fun obtenerOutfitsPorFecha(idUsuario: Int, fecha: String): Flow<List<OutfitEntity>>

    @Query("""
        SELECT p.* FROM prenda p
        INNER JOIN prenda_outfit po ON p.id = po.idPrenda
        WHERE po.idOutfit = :idOutfit
    """)
    fun obtenerPrendasDeOutfit(idOutfit: Int): Flow<List<PrendaEntity>>
}
