package ramirez.ruben.closetvirtual.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "prenda",
    foreignKeys = [
        ForeignKey(
            entity = UsuarioEntity::class,
            parentColumns = ["id"],
            childColumns = ["idUsuario"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PrendaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val idUsuario: Int,
    val nombre: String,
    val marca: String?,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    val imagen: ByteArray?,
    val categoria: String,
    val color: String,
    val estampada: Boolean,
    val talla: String?,
    val temporada: String,
    val formalidad: String,
    val tags: List<String>,
    val favorito: Boolean = false
)
