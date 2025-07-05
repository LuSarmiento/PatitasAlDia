package com.lu.patitasaldia.pendientes

import java.text.SimpleDateFormat
import java.util.*

fun PendienteMedico.fechaFormatoDate(): Date {
    return try {
        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(this.fecha) ?: Date(0)
    } catch (e: Exception) {
        Date(0)
    }
}