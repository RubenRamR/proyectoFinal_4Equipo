package ramirez.ruben.closetvirtual.data.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "prenda_outfit",
    foreignKeys = [
        ForeignKey(
            entity = PrendaEntity::class,
            parentColumns = ["id"],
            childColumns = ["idPrenda"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = OutfitEntity::class,
            parentColumns = ["id"],
            childColumns = ["idOutfit"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PrendaOutfitEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val idPrenda: Int,
    val idOutfit: Int
)
