package ramirez.ruben.closetvirtual.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ramirez.ruben.closetvirtual.data.database.entity.UsuarioEntity

@Dao
interface UsuarioDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarUsuario(usuario: UsuarioEntity): @JvmSuppressWildcards Long

    @Update
    suspend fun actualizarUsuario(usuario: UsuarioEntity): @JvmSuppressWildcards Int

    @Query("SELECT * FROM usuarios WHERE correo = :correo AND contrasena = :contrasena LIMIT 1")
    suspend fun login(correo: String, contrasena: String): @JvmSuppressWildcards UsuarioEntity?

    @Query("SELECT * FROM usuarios WHERE correo = :correo LIMIT 1")
    suspend fun obtenerUsuarioPorCorreo(correo: String): @JvmSuppressWildcards UsuarioEntity?

    @Query("SELECT * FROM usuarios WHERE id = :id LIMIT 1")
    suspend fun obtenerUsuarioPorId(id: String): @JvmSuppressWildcards UsuarioEntity?

}
