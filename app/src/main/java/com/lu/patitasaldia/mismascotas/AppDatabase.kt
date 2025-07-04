package com.lu.patitasaldia.mismascotas

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lu.patitasaldia.mismascotas.Mascota
import com.lu.patitasaldia.pendientes.PendienteMedico
import com.lu.patitasaldia.pendientes.PendienteMedicoDao

@Database(entities = [Mascota::class, PendienteMedico::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun mascotaDao(): MascotaDao
    abstract fun pendienteMedicoDao(): PendienteMedicoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "patitas-db"
                )
                    .fallbackToDestructiveMigration() // <- Agregá esto
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}