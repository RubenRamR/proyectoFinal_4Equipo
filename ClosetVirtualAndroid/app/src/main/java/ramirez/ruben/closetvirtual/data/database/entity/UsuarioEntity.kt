package ramirez.ruben.closetvirtual.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "usuarios")
data class UsuarioEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val correo: String,
    val contrasena: String,
    val fechaNacimiento: String,
    val genero: String,

    // Ocupamos esto para guardar la foto de perfil, guardando la imagen como un arreglo de bytes en la BD
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    val fotoPerfil: ByteArray? = null,

    val isBiometricsEnabled: Boolean = false,
    val isDarkThemeEnabled: Boolean = false
)