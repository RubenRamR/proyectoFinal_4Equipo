package ramirez.ruben.closetvirtual.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ramirez.ruben.closetvirtual.data.database.entity.PrendaEntity

@Dao
interface PrendaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarPrenda(prenda: PrendaEntity): @JvmSuppressWildcards Long

    @Update
    suspend fun actualizarPrenda(prenda: PrendaEntity): @JvmSuppressWildcards Int

    @Delete
    suspend fun eliminarPrenda(prenda: PrendaEntity): @JvmSuppressWildcards Int

    @Query("SELECT * FROM prendas ORDER BY fechaRegistro DESC")
    fun obtenerTodasLasPrendas(): Flow<List<PrendaEntity>>

    @Query("SELECT * FROM prendas WHERE id = :id")
    suspend fun obtenerPrendaPorId(id: String): @JvmSuppressWildcards PrendaEntity?
}