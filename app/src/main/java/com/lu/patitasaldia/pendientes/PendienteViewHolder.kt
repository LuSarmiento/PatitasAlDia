package com.lu.patitasaldia.pendientes

import android.view.View
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lu.patitasaldia.R

class PendienteViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val tvDescripcion: TextView = view.findViewById(R.id.tvDescripcionPendiente)
    private val tvFecha: TextView = view.findViewById(R.id.tvFechaPendiente)
    private val tvTipo: TextView = view.findViewById(R.id.tvTipoPendiente)

    fun render(
        pendiente: PendienteMedico,
        onMarcarTerminado: (PendienteMedico) -> Unit,
        onEliminar: (PendienteMedico) -> Unit,
        onEditar: (PendienteMedico) -> Unit
    ) {
        tvDescripcion.text = pendiente.descripcion
        tvFecha.text = pendiente.fecha
        tvTipo.text = pendiente.tipo

        itemView.findViewById<ImageView>(R.id.ivMenuPendiente).setOnClickListener { view ->
            val popup = PopupMenu(view.context, view)
            popup.inflate(R.menu.menu_pendiente)
            popup.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_completar -> onMarcarTerminado(pendiente)
                    R.id.menu_editar -> onEditar(pendiente)
                    R.id.menu_eliminar -> onEliminar(pendiente)
                }
                true
            }
            popup.show()
        }
    }


}