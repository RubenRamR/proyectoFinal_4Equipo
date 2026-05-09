package ramirez.ruben.closetvirtual.data.database.converters

import androidx.room.TypeConverter

class StringListConverter {

    @TypeConverter
    fun fromStringList(value: List<String>): String {
        if (value.isEmpty()) return ""
        return value.joinToString(separator = ",")
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        if (value.isBlank()) return emptyList()
        return value.split(",").map { it.trim() }
    }
}