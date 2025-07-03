package com.lu.patitasaldia.mismascotas

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lu.patitasaldia.mismascotas.Mascota
import com.lu.patitasaldia.R

class MascotasAdapter(private var mascotas: MutableList<Mascota>) :
    RecyclerView.Adapter<MascotasViewHolder>() {
    var onItemClicked: ((Mascota) -> Unit)? = null
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
        holder.render(mascotas[position]) { mascota ->
            onItemClicked?.invoke(mascota)
        }
    }

    override fun getItemCount() = mascotas.size

    fun actualizarLista(nuevaLista: List<Mascota>) {
        mascotas.clear()
        mascotas.addAll(nuevaLista)
        notifyDataSetChanged()
    }

}