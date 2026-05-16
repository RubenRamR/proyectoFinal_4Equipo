package ramirez.ruben.closetvirtual.data.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

// No supe de otra forma de poder mostrar en el CalendarioScreen el historial de uso de un outfit
// asi que hice esta clase entity para tener una nueva tabla en la BD que registra el historial de uso
// (capaz si habia otra forma pero ni idea)
@Entity(
    tableName = "historial_uso_outfit",
    foreignKeys = [
        ForeignKey(
            entity = OutfitEntity::class,
            parentColumns = ["id"],
            childColumns = ["idOutfit"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class HistorialUsoOutfitEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val idOutfit: Int,
    val fechaUso: String
)