package com.lu.patitasaldia.pendientes

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PendienteMedicoDao {

    @Insert
    suspend fun insertar(pendiente: PendienteMedico)

    @Update
    suspend fun actualizar(pendiente: PendienteMedico)

    @Delete
    suspend fun eliminar(pendiente: PendienteMedico)

    @Query("SELECT * FROM PendienteMedico WHERE completado=0")
    suspend fun obtenerPendientesActivos(): List<PendienteMedico>

    @Query("SELECT * FROM PendienteMedico WHERE completado=1")
    suspend fun obtenerHisorial(): List<PendienteMedico>

    @Query("SELECT * FROM PendienteMedico WHERE id=:id")
    suspend fun obtenerPorId(id: Int): PendienteMedico

}