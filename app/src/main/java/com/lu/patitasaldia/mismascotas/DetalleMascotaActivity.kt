package com.lu.patitasaldia.mismascotas

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.room.util.findColumnIndexBySuffix
import com.lu.patitasaldia.R
import com.lu.patitasaldia.mismascotas.agregarmascota.AgregarMascotaActivity
import kotlinx.coroutines.launch

class DetalleMascotaActivity : AppCompatActivity() {

    private lateinit var tvNombreTitulo: TextView
    private lateinit var tvNombre: TextView
    private lateinit var tvEspecie: TextView
    private lateinit var tvRaza: TextView
    private lateinit var tvSexo: TextView
    private lateinit var tvNacimiento: TextView
    private lateinit var tvCastrado: TextView

    private var mascota: Mascota? = null

    private lateinit var cvEditar: CardView
    private lateinit var cvEliminar: CardView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalle_mascota)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initComponents()

        val idMascota = intent.getIntExtra("idMascota", -1)

        if (idMascota != -1) {
            val db = AppDatabase.getDatabase(this)
            val mascotaDao = db.mascotaDao()

            lifecycleScope.launch {
                mascota = mascotaDao.obtenerPorId(idMascota)
                mascota?.let {
                    tvNombreTitulo.text = it.nombre
                    tvNombre.text = it.nombre
                    tvEspecie.text = it.especie
                    tvRaza.text = it.raza
                    tvSexo.text = it.sexo
                    tvNacimiento.text = it.fechaNacimiento
                    tvCastrado.text = if (it.castrado) "Sí" else "No"

                    initListeners(mascotaDao)
                }
            }
        }

    }

    private fun initListeners(mascotaDao: MascotaDao) {
        cvEliminar.setOnClickListener {

            mascota?.let { mascotaSeleccionada ->
                AlertDialog.Builder(this)
                    .setTitle("¿Eliminar mascota?")
                    .setMessage("¿Estás segura de que querés eliminar a ${mascotaSeleccionada.nombre}? Esta acción no se puede deshacer.")
                    .setPositiveButton("Eliminar") { _, _ ->
                        lifecycleScope.launch {
                            mascotaDao.eliminarMascota(mascotaSeleccionada)
                            finish()
                        }
                    }
                    .setNegativeButton("Cancelar", null)
                    .show()
            }

        //            mascota?.let {
//                lifecycleScope.launch {
//                    mascotaDao.eliminarMascota(it)
//                    finish()
//                }
//            }
        }
        cvEditar.setOnClickListener {
            mascota?.let {
                val intent = Intent(this, AgregarMascotaActivity::class.java)
                intent.putExtra("idMascota", it.id)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun initComponents() {
        tvNombreTitulo = findViewById(R.id.tvNombreTitulo)
        tvNombre = findViewById(R.id.tvNombre)
        tvEspecie = findViewById(R.id.tvEspecie)
        tvRaza = findViewById(R.id.tvRaza)
        tvSexo = findViewById(R.id.tvSexo)
        tvNacimiento = findViewById(R.id.tvFechaNacimiento)
        tvCastrado = findViewById(R.id.tvCastrado)

        cvEditar = findViewById(R.id.cvEditar)
        cvEliminar = findViewById(R.id.cvEliminar)
    }

}