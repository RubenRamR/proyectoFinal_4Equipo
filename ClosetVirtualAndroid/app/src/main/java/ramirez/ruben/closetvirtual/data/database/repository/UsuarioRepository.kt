package ramirez.ruben.closetvirtual.data.database.repository

import ramirez.ruben.closetvirtual.data.database.dao.UsuarioDao
import ramirez.ruben.closetvirtual.data.database.entity.UsuarioEntity

class UsuarioRepository(private val usuarioDao: UsuarioDao) {

    suspend fun registrarUsuario(usuario: UsuarioEntity): Long {
        return usuarioDao.insertarUsuario(usuario)
    }

    suspend fun actualizarUsuario(usuario: UsuarioEntity) {
        usuarioDao.actualizarUsuario(usuario)
    }

    suspend fun login(correo: String, contrasena: String): UsuarioEntity? {
        return usuarioDao.login(correo, contrasena)
    }

    suspend fun existeCorreo(correo: String): Boolean {
        return usuarioDao.obtenerUsuarioPorCorreo(correo) != null
    }

    suspend fun obtenerUsuarioPorId(id: String): UsuarioEntity? {
        return usuarioDao.obtenerUsuarioPorId(id)
    }
}