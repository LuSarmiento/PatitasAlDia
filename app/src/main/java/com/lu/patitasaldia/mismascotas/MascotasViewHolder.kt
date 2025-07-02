package com.lu.patitasaldia.mismascotas

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lu.patitasaldia.mismascotas.Mascota
import com.lu.patitasaldia.R

class MascotasViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val tvMascotasNombres: TextView = view.findViewById(R.id.tvMascotasNombres)
    private val ivIconoMascotas: ImageView = view.findViewById(R.id.ivIconoMascotas)

    fun render(mascota: Mascota, onClick: (Mascota) -> Unit) {
        tvMascotasNombres.text = mascota.nombre

        itemView.setOnClickListener {
            onClick(mascota)
        }
    }

}