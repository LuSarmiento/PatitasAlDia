package com.lu.patitasaldia.mismascotas.agregarmascota

data class Mascota(
    val nombre: String,
    val especie: String,
    val raza: String,
    val sexo: String,
    val fechaNacimiento: String,
    val castrado: Boolean
)