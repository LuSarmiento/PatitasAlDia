package com.lu.patitasaldia

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.lu.patitasaldia.historialmedico.HistorialMedicoActivity
import com.lu.patitasaldia.mismascotas.AppDatabase
import com.lu.patitasaldia.mismascotas.MisMascotasActivity
import com.lu.patitasaldia.pendientes.AgregarPendienteActivity
import com.lu.patitasaldia.pendientes.PendienteAdapter
import com.lu.patitasaldia.pendientes.PendienteMedico
import com.lu.patitasaldia.pendientes.PendienteMedicoDao
import kotlinx.coroutines.launch

class MenuActivity : AppCompatActivity() {

    private lateinit var cvMisMascotas: CardView
    private lateinit var cvHistorialMedico: CardView
    private lateinit var fabAgregarPendiente: FloatingActionButton
    private lateinit var rvPendientes: RecyclerView
    private lateinit var pendientesAdapter: PendienteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initComponents()
        initListeners()
        initUI()

    }

    override fun onResume() {
        super.onResume()
        cargarPendientes()
    }

    private fun initUI() {
        rvPendientes.layoutManager = LinearLayoutManager(this)

        pendientesAdapter = PendienteAdapter(
            mutableListOf(),
            onMarcarTerminado = { pendiente -> mostrarDialogoConfirmacion(pendiente) },
            onEditar = { pendiente -> editarPendiente(pendiente) },
            onEliminar = { pendiente -> eliminarPendiente(pendiente) }
        )

        rvPendientes.adapter = pendientesAdapter
        lifecycleScope.launch {
            val db = AppDatabase.getDatabase(this@MenuActivity)
            val pendienteDao = db.pendienteMedicoDao()
            val lista = pendienteDao.obtenerPendientesActivos()
            pendientesAdapter.actualizarLista(lista)
        }
    }

    private fun initComponents() {
        cvMisMascotas = findViewById(R.id.cvMisMascotas)
        cvHistorialMedico = findViewById(R.id.cvHistorialMedico)
        fabAgregarPendiente = findViewById(R.id.fabAgregarPendiente)
        rvPendientes = findViewById(R.id.rvPendientes)
    }

    private fun initListeners() {
        cvMisMascotas.setOnClickListener { navigateToMisMascotas() }
        cvHistorialMedico.setOnClickListener { navigateToHistorialMedico() }
        fabAgregarPendiente.setOnClickListener {
            val intent = Intent(this, com.lu.patitasaldia.pendientes.AgregarPendienteActivity::class.java)
            startActivity(intent)
        }
    }

    private fun mostrarDialogoConfirmacion(pendiente: PendienteMedico) {
        AlertDialog.Builder(this)
            .setTitle("¿Marcar como completado?")
            .setMessage("¿Querés marcar este pendiente como completado?\n\n${pendiente.descripcion}")
            .setPositiveButton("Sí") { _, _ ->
                lifecycleScope.launch {
                    val pendienteCompletado = pendiente.copy(completado = true)
                    val db = AppDatabase.getDatabase(this@MenuActivity)
                    db.pendienteMedicoDao().actualizar(pendienteCompletado)
                    cargarPendientes()
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun eliminarPendiente(pendiente: PendienteMedico) {
        AlertDialog.Builder(this)
            .setTitle("¿Eliminar pendiente?")
            .setMessage("¿Seguro que querés eliminar este pendiente?\n\n${pendiente.descripcion}")
            .setPositiveButton("Eliminar") { _, _ ->
                lifecycleScope.launch {
                    val db = AppDatabase.getDatabase(this@MenuActivity)
                    db.pendienteMedicoDao().eliminar(pendiente)
                    cargarPendientes()
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun editarPendiente(pendiente: PendienteMedico) {
        val intent = Intent(this, AgregarPendienteActivity::class.java)
        intent.putExtra("idPendiente", pendiente.id)
        startActivity(intent)
    }

    private fun cargarPendientes() {
        val db = AppDatabase.getDatabase(this)
        val pendienteDao = db.pendienteMedicoDao()

        lifecycleScope.launch {
            val pendientesDesdeDB = pendienteDao.obtenerPendientesActivos()
            pendientesAdapter.actualizarLista(pendientesDesdeDB)
        }
    }

    private fun navigateToMisMascotas() {
        val intent = Intent(this, MisMascotasActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToHistorialMedico() {
        val intent = Intent(this, HistorialMedicoActivity::class.java)
        startActivity(intent)

    }

}