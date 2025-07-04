package com.lu.patitasaldia.mismascotas.agregarmascota

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.lu.patitasaldia.R
import com.lu.patitasaldia.mismascotas.Mascota
import com.lu.patitasaldia.mismascotas.AppDatabase
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import java.util.Calendar


class AgregarMascotaActivity : AppCompatActivity() {

    private lateinit var cvGuardar: CardView

    private var idMascota: Int? = null

    private lateinit var etNombre: EditText
    private lateinit var etEspecie: EditText
    private lateinit var etRaza: EditText
    private lateinit var rgSexo: RadioGroup
    private lateinit var etNacimiento: EditText
    private lateinit var cbCastrado: CheckBox


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_agregar_mascota)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initComponent()
        initListeners()

        idMascota = intent.getIntExtra("idMascota", -1).takeIf { it != -1 }

        if (idMascota != null) {
            lifecycleScope.launch {
                val db = AppDatabase.getDatabase(this@AgregarMascotaActivity)
                val mascota = db.mascotaDao().obtenerPorId(idMascota!!)
                mascota?.let {
                    etNombre.setText(it.nombre)
                    etEspecie.setText(it.especie)
                    etRaza.setText(it.raza)
                    etNacimiento.setText(it.fechaNacimiento)
                    cbCastrado.isChecked = it.castrado
                    rgSexo.check(if (it.sexo == "Femenino") R.id.rbFemenino else R.id.rbMasculino)
                }
            }
        }
    }

    private fun initComponent() {
        cvGuardar = findViewById<CardView>(R.id.cvGuardar)

        etNombre = findViewById(R.id.etNombre)
        etEspecie = findViewById(R.id.etEspecie)
        etRaza = findViewById(R.id.etRaza)
        rgSexo = findViewById<RadioGroup>(R.id.rgSexo)
        etNacimiento = findViewById(R.id.etFechaNacimiento)
        cbCastrado = findViewById(R.id.cbCastrado)
    }

    private fun initListeners() {
        cvGuardar.setOnClickListener {

            val nombre = etNombre.text.toString().trim()

            if (nombre.isEmpty()) {
                etNombre.error = "Este campo es obligatorio"
                etNombre.requestFocus()
                return@setOnClickListener
            }

            val db = AppDatabase.getDatabase(applicationContext)
            val mascotaDao = db.mascotaDao()

            val mascota = Mascota(
                id = idMascota?: 0,
                nombre = etNombre.text.toString(),
                especie = etEspecie.text.toString(),
                raza = etRaza.text.toString(),
                fechaNacimiento = etNacimiento.text.toString(),
                sexo = sexoSeleccionado(),
                castrado = cbCastrado.isChecked
            )

            lifecycleScope.launch {
                if (idMascota!=null) {
                    mascotaDao.actualizar(mascota)
                }
                else{
                    mascotaDao.insertarMascota(mascota)
                }
                finish()
            }
        }

        etNacimiento.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePicker = DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    val fechaFormateada = "%02d/%02d/%04d".format(dayOfMonth, month + 1, year)
                    etNacimiento.setText(fechaFormateada)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePicker.show()
        }
    }

    private fun sexoSeleccionado(): String {
        return when (rgSexo.checkedRadioButtonId) {
            R.id.rbFemenino -> "Femenino"
            R.id.rbMasculino -> "Masculino"
            else -> "-"
        }
    }

}