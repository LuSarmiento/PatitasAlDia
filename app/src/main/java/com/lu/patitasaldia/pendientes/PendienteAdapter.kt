package com.lu.patitasaldia.pendientes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lu.patitasaldia.R

class PendienteAdapter(
    private var pendientes: MutableList<PendienteMedico>,
    private val onMarcarTerminado: (PendienteMedico) -> Unit,
    private val onEliminar: (PendienteMedico) -> Unit,
    private val onEditar: (PendienteMedico) -> Unit

): RecyclerView.Adapter<PendienteViewHolder>(){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PendienteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pendiente, parent, false)
        return PendienteViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: PendienteViewHolder,
        position: Int
    ) {
        holder.render(
            pendientes[position],
            onMarcarTerminado,
            onEliminar,
            onEditar
        )
    }

    override fun getItemCount() = pendientes.size

    fun actualizarLista(nuevosPendientes: List<PendienteMedico>){
        pendientes.clear()
        pendientes.addAll(nuevosPendientes)
        notifyDataSetChanged()
    }

}



