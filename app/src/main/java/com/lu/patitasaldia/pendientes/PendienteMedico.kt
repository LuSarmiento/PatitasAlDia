package com.lu.patitasaldia.pendientes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PendienteMedico (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val titulo: String,
    val descripcion: String,
    val fecha: String,
    val tipo: String,
    val mascotaId: Int,
    val completado: Boolean = false
)