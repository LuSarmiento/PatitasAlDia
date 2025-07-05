package com.lu.patitasaldia.historialmedico

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lu.patitasaldia.R
import com.lu.patitasaldia.mismascotas.AppDatabase
import com.lu.patitasaldia.mismascotas.Mascota
import com.lu.patitasaldia.pendientes.HistorialAdapter
import com.lu.patitasaldia.pendientes.PendienteMedico
import com.lu.patitasaldia.pendientes.fechaFormatoDate
import kotlinx.coroutines.launch

class HistorialMedicoActivity : AppCompatActivity() {

    private lateinit var spFiltroMascota: Spinner
    private lateinit var spFiltroTipo: Spinner
    private lateinit var listaMascotas: List<Mascota>
    private lateinit var historialAdapter: HistorialAdapter
    private lateinit var rvHistorial: RecyclerView

    val db = AppDatabase.getDatabase(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_historial_medico)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initComponents()
        initFiltros()
        initListeners()

    }

    private fun initComponents() {
        spFiltroMascota = findViewById(R.id.spFiltroMascota)
        spFiltroTipo = findViewById(R.id.spFiltroTipo)
        rvHistorial = findViewById(R.id.rvHistorial)
        rvHistorial.layoutManager = LinearLayoutManager(this)
        historialAdapter = HistorialAdapter(mutableListOf()) { pendiente ->
            mostrarDialogoEliminar(pendiente)
        }
        rvHistorial.adapter = historialAdapter

    }

    private fun initFiltros() {
        lifecycleScope.launch {
            listaMascotas = db.mascotaDao().obtenerTodas()
            // Cargar mascotas
            val nombresMascotas = listOf("Todas las mascotas") + listaMascotas.map { it.nombre }
            val adapterMascotas = ArrayAdapter(
                this@HistorialMedicoActivity,
                android.R.layout.simple_spinner_dropdown_item,
                nombresMascotas
            )
            spFiltroMascota.adapter = adapterMascotas

            // Cargar tipos
            val tipos = listOf(
                "Todos los tipos",
                "Vacuna",
                "Medicamento",
                "Antiparasitario",
                "Visita al veterinario"
            )
            val adapterTipos = ArrayAdapter(
                this@HistorialMedicoActivity,
                android.R.layout.simple_spinner_dropdown_item,
                tipos
            )
            spFiltroTipo.adapter = adapterTipos

            // Cargar historial por primera vez
            cargarHistorial()
        }
    }

    private fun initListeners() {
        spFiltroMascota.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                cargarHistorial()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        spFiltroTipo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                cargarHistorial()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun cargarHistorial() {
        val dao = db.pendienteMedicoDao()

        lifecycleScope.launch {
            var pendientes = dao.obtenerHisorial()

            // Filtro por mascota
            val indexMascota = spFiltroMascota.selectedItemPosition
            if (indexMascota > 0) {
                val idMascota = listaMascotas[indexMascota - 1].id
                pendientes = pendientes.filter { it.mascotaId == idMascota }
            }

            // Filtro por tipo
            val tipoSeleccionado = spFiltroTipo.selectedItem.toString()
            if (tipoSeleccionado != "Todos los tipos") {
                pendientes = pendientes.filter { it.tipo == tipoSeleccionado }
            }

            val pendientesOrdenados = pendientes.sortedBy { it.fechaFormatoDate() }
            historialAdapter.actualizarLista(pendientesOrdenados)

        }
    }

    private fun mostrarDialogoEliminar(pendiente: PendienteMedico) {
        val builder = androidx.appcompat.app.AlertDialog.Builder(this)
        builder.setTitle("¿Eliminar pendiente?")
        builder.setMessage("¿Estás seguro de que querés eliminar este pendiente del historial?")
        builder.setPositiveButton("Sí") { _, _ ->
            lifecycleScope.launch {
                val dao = AppDatabase.getDatabase(this@HistorialMedicoActivity).pendienteMedicoDao()
                dao.eliminar(pendiente)
                cargarHistorial()
            }
        }
        builder.setNegativeButton("Cancelar", null)
        builder.show()
    }

}