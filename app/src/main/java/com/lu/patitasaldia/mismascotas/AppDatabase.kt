package com.lu.patitasaldia.mismascotas

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lu.patitasaldia.mismascotas.Mascota

@Database(entities = [Mascota::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun mascotaDao(): MascotaDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "patitas_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}