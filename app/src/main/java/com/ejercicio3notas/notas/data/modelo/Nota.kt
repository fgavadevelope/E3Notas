package com.ejercicio3notas.notas.data.modelo

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update
import com.ejercicio3notas.notas.TaskCategory
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "notas")
data class Nota(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val category: TaskCategory,
    val name: String
){
    constructor(category: TaskCategory, name: String) : this(0, category, name)
}

@Dao
interface NotaDao {
    @Query("SELECT * FROM notas")
    fun getAll(): Flow<List<Nota>>

    @Insert
    suspend fun insert(nota: Nota)

    @Update
    suspend fun update(nota: Nota)

    @Delete
    suspend fun delete(nota: Nota)
}