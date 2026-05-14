package ramirez.ruben.closetvirtual.data.database.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation


// TODO
//class OutfitWithPrenda (
//    @Embedded
//    val outfit: ComboEntity,
//
//    @Relation(
//        parentColumn = "comboId",
//        entityColumn = "productId",
//        associateBy = Junction(
//            value = ProductComboEntity::class,
//            parentColumn = "comboId",
//            entityColumn = "productId"
//        )
//    )
//    val products: List<ProductEntity>
//
//)