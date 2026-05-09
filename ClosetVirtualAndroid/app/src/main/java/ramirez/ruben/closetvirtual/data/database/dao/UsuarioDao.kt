package ramirez.ruben.closetvirtual.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ramirez.ruben.closetvirtual.data.database.entity.UsuarioEntity

@Dao
interface UsuarioDao {
    @Insert
    suspend fun registrarUsuario(usuario: UsuarioEntity): Long

    @Query("SELECT * FROM usuario WHERE correo = :correo AND password = :password LIMIT 1")
    suspend fun login(correo: String, password: String): UsuarioEntity?
}
