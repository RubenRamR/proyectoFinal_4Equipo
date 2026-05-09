package ramirez.ruben.closetvirtual.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "prendas")
data class PrendaEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val nombre: String,
    val marca: String? = null,
    val imagenUri: String,
    val categoria: String,
    val color: String,
    val esEstampada: Boolean = false,
    val talla: String? = null,
    val temporada: String,
    val formalidad: String,
    val tags: List<String> = emptyList(),
    val fechaRegistro: Long = System.currentTimeMillis()
)