package com.lu.patitasaldia.mismascotas

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lu.patitasaldia.mismascotas.agregarmascota.Mascota
import com.lu.patitasaldia.R

class MascotasAdapter(private val mascotas: List<Mascota>) :
    RecyclerView.Adapter<MascotasViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MascotasViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_mascotas, parent, false)
            return MascotasViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: MascotasViewHolder,
        position: Int
    ) {
        holder.render(mascotas[position])
    }

    override fun getItemCount() = mascotas.size

}