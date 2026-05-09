package ramirez.ruben.closetvirtual.data.database.repository

import ramirez.ruben.closetvirtual.data.database.dao.UsuarioDao
import ramirez.ruben.closetvirtual.data.database.entity.UsuarioEntity

class UsuarioRepository(private val usuarioDao: UsuarioDao) {
    suspend fun registrarUsuario(usuario: UsuarioEntity): Long {
        return usuarioDao.registrarUsuario(usuario)
    }

    suspend fun login(correo: String, password: String): UsuarioEntity? {
        return usuarioDao.login(correo, password)
    }
}
