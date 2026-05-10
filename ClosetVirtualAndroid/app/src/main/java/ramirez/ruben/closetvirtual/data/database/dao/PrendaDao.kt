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
    suspend fun insertarPrenda(prenda: PrendaEntity): Long

    @Update
    suspend fun actualizarPrenda(prenda: PrendaEntity): Int

    @Delete
    suspend fun eliminarPrenda(prenda: PrendaEntity): Int

    @Query("SELECT * FROM prenda")
    fun obtenerTodasLasPrendas(): Flow<List<PrendaEntity>>

    @Query("SELECT * FROM prenda WHERE idUsuario = :idUsuario")
    fun obtenerPrendasPorUsuario(idUsuario: Int): Flow<List<PrendaEntity>>

    @Query("SELECT * FROM prenda WHERE id = :id")
    suspend fun obtenerPrendaPorId(id: Int): PrendaEntity?

    @Query("UPDATE prenda SET favorito = :isFav WHERE id = :id")
    suspend fun actualizarFavorito(id: Int, isFav: Boolean): Int
}
