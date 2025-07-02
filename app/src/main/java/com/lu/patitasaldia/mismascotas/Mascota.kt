package com.lu.patitasaldia.mismascotas

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "mascotas")
data class Mascota(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val nombre: String,
    val especie: String,
    val raza: String,
    val sexo: String,
    val fechaNacimiento: String,
    val castrado: Boolean
): Parcelable