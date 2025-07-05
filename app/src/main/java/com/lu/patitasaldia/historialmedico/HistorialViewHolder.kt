package com.lu.patitasaldia.pendientes

import android.view.View
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lu.patitasaldia.R

class HistorialViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val tvDescripcion: TextView = view.findViewById(R.id.tvDescripcionPendiente)
    private val tvFecha: TextView = view.findViewById(R.id.tvFechaPendiente)
    private val tvTipo: TextView = view.findViewById(R.id.tvTipoPendiente)
    private val ivMenu: ImageView = view.findViewById(R.id.ivMenuPendiente)

    fun render(
        pendiente: PendienteMedico,
        onEliminar: (PendienteMedico) -> Unit
    ) {
        tvDescripcion.text = pendiente.descripcion
        tvFecha.text = pendiente.fecha
        tvTipo.text = pendiente.tipo

        ivMenu.setOnClickListener {
            val popup = PopupMenu(itemView.context, ivMenu)
            popup.menu.add("Eliminar").setOnMenuItemClickListener {
                onEliminar(pendiente)
                true
            }
            popup.show()
        }
    }

}