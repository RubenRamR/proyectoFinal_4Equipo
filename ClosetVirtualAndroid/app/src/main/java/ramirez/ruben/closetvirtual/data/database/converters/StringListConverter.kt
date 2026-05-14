package ramirez.ruben.closetvirtual.data.database.converters

import androidx.room.TypeConverter
//Todo
class StringListConverter {

    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return value.joinToString(separator = ",")
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        return if (value.isBlank()) emptyList() else value.split(",")
    }
}