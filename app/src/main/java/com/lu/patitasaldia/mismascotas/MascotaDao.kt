package com.lu.patitasaldia.mismascotas

import androidx.room.*
import com.lu.patitasaldia.mismascotas.Mascota

@Dao
interface MascotaDao {

    @Insert
    suspend fun insertarMascota(mascota: Mascota)

    @Query("SELECT * FROM mascotas")
    suspend fun obtenerTodas(): List<Mascota>

    @Query("SELECT * FROM mascotas WHERE id = :id")
    suspend fun obtenerPorId(id: Int): Mascota?

    @Delete
    suspend fun eliminarMascota(mascota: Mascota)

    @Update
    suspend fun actualizar(mascota: Mascota)

}