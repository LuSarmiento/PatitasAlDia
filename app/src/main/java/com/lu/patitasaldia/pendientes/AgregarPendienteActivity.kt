package com.lu.patitasaldia.pendientes

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.lu.patitasaldia.R
import com.lu.patitasaldia.mismascotas.AppDatabase
import com.lu.patitasaldia.mismascotas.Mascota
import kotlinx.coroutines.launch
import java.util.Calendar

class AgregarPendienteActivity : AppCompatActivity() {

    private var idPendiente: Int? = null
    private lateinit var listaMascotas: List<Mascota>

    private lateinit var etDescripcion: EditText
    private lateinit var etFecha: EditText
    private lateinit var spTipo: Spinner
    private lateinit var spMascota: Spinner
    private lateinit var cvGuardar: CardView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_agregar_pendiente)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initComponents()
        initListeners()

        idPendiente = intent.getIntExtra("idPendiente", -1).takeIf { it != -1 }

        val db = AppDatabase.getDatabase(this)
        val dao = db.mascotaDao()

        val tipos = listOf("Vacuna", "Medicamento", "Antiparasitario", "Visita al veterinario")
        val tipoAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, tipos)
        tipoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spTipo.adapter = tipoAdapter


        lifecycleScope.launch {
            listaMascotas = dao.obtenerTodas()
            val nombres = listaMascotas.map { it.nombre }
            val adapter = ArrayAdapter(
                this@AgregarPendienteActivity,
                android.R.layout.simple_spinner_item,
                nombres
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spMascota.adapter = adapter



            if (idPendiente != null) {
                val daoPendiente =
                    AppDatabase.getDatabase(this@AgregarPendienteActivity).pendienteMedicoDao()
                val pendiente = daoPendiente.obtenerPorId(idPendiente!!)

                etDescripcion.setText(pendiente.descripcion)
                etFecha.setText(pendiente.fecha)
                spTipo.setSelection(tipos.indexOf(pendiente.tipo))

                val indexMascota = listaMascotas.indexOfFirst { it.id == pendiente.mascotaId }
                if (indexMascota != -1) {
                    spMascota.setSelection(indexMascota)
                }
            }

            val mascotaSeleccionada = spMascota.selectedItemPosition
            val idMascotaSeleccionada = listaMascotas[mascotaSeleccionada].id
        }
    }

    private fun initListeners() {
        etFecha.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePicker = DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    val fechaFormateada = "%02d/%02d/%04d".format(dayOfMonth, month + 1, year)
                    etFecha.setText(fechaFormateada)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePicker.show()
        }

        cvGuardar.setOnClickListener {
            val pendiente = PendienteMedico(
                id = idPendiente ?: 0,
                descripcion = etDescripcion.text.toString(),
                fecha = etFecha.text.toString(),
                tipo = spTipo.selectedItem.toString(),
                mascotaId = listaMascotas[spMascota.selectedItemPosition].id,
                completado = false
            )

            lifecycleScope.launch {
                val dao =
                    AppDatabase.getDatabase(this@AgregarPendienteActivity).pendienteMedicoDao()

                if (idPendiente == null) {
                    dao.insertar(pendiente)
                } else {
                    dao.actualizar(pendiente)
                }

                finish()
            }
        }

    }


    private fun initComponents() {
        etDescripcion = findViewById(R.id.etDescripcionPendiente)
        etFecha = findViewById(R.id.etFecha)
        spTipo = findViewById(R.id.spTipoPendiente)
        spMascota = findViewById(R.id.spMascotas)
        cvGuardar = findViewById(R.id.cvGuardar)
    }

}

