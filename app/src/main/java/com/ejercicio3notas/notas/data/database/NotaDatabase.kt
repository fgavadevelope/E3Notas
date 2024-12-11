package com.ejercicio3notas.notas.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ejercicio3notas.notas.data.modelo.Nota
import com.ejercicio3notas.notas.data.modelo.NotaDao

@Database(entities = [Nota::class], version = 1)
@TypeConverters(MisConverters::class)
abstract class NotaDatabase : RoomDatabase() {
    abstract fun notaDao(): NotaDao

    companion object {
        @Volatile
        private var INSTANCE: NotaDatabase? = null

        fun getDatabase(context: Context): NotaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotaDatabase::class.java,
                    "nota_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}