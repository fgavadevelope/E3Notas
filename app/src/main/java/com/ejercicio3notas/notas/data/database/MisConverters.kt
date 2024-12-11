package com.ejercicio3notas.notas.data.database
import androidx.room.TypeConverter
import com.ejercicio3notas.notas.TaskCategory

class MisConverters  {
/*    @TypeConverter
    fun fromTaskCategory(category: TaskCategory?): String? {
        return category?.toString()
    }

    @TypeConverter
    fun toTaskCategory(categoryName: String?): TaskCategory {
        return TaskCategory.fromString(categoryName)
    }*/


    @TypeConverter
    fun fromTaskCategory(category: TaskCategory?): String? {
        return category?.javaClass?.simpleName  // Se guarda como el nombre de la clase (por ejemplo, "Personal")
    }

    // Convertir String a TaskCategory
    @TypeConverter
    fun toTaskCategory(categoryName: String?): TaskCategory? {
        return when (categoryName) {
            TaskCategory.Personal::class.java.simpleName -> TaskCategory.Personal
            TaskCategory.Business::class.java.simpleName -> TaskCategory.Business
            TaskCategory.Others::class.java.simpleName -> TaskCategory.Others
            else -> TaskCategory.Personal
        }
    }
}