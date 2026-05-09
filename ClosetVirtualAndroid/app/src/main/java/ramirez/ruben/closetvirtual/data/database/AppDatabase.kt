package ramirez.ruben.closetvirtual.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ramirez.ruben.closetvirtual.data.database.converters.StringListConverter
import ramirez.ruben.closetvirtual.data.database.dao.PrendaDao
import ramirez.ruben.closetvirtual.data.database.entity.PrendaEntity

@Database(
    entities = [
        PrendaEntity::class,
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(StringListConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun prendaDao(): PrendaDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "closet_virtual.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}