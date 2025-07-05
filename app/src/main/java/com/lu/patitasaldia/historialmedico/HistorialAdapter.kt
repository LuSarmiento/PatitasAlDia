package com.lu.patitasaldia.pendientes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lu.patitasaldia.R

class HistorialAdapter(
    private var pendientes: MutableList<PendienteMedico>,
    private val onEliminar: (PendienteMedico) -> Unit

): RecyclerView.Adapter<HistorialViewHolder>(){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistorialViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pendiente, parent, false)
        return HistorialViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: HistorialViewHolder,
        position: Int
    ) {
        holder.render(
            pendientes[position],
            onEliminar
        )
    }

    override fun getItemCount() = pendientes.size

    fun actualizarLista(nuevosPendientes: List<PendienteMedico>){
        pendientes.clear()
        pendientes.addAll(nuevosPendientes)
        notifyDataSetChanged()
    }

}



