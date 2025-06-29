package com.lu.patitasaldia.mismascotas.agregarmascota

import android.app.Activity
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
import com.lu.patitasaldia.mismascotas.agregarmascota.Mascota

class AgregarMascotaActivity : AppCompatActivity() {

    private lateinit var cvGuardar: CardView

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


    }

    private fun initComponent(){
        cvGuardar = findViewById<CardView>(R.id.cvGuardar)

        etNombre = findViewById<EditText>(R.id.etNombre)
        etEspecie = findViewById<EditText>(R.id.etEspecie)
        etRaza = findViewById<EditText>(R.id.etRaza)
        rgSexo= findViewById<RadioGroup>(R.id.rgSexo)
        etNacimiento = findViewById<EditText>(R.id.etFechaNacimiento)
        cbCastrado = findViewById<CheckBox>(R.id.cbCastrado)

    }

    private fun initListeners(){
        cvGuardar.setOnClickListener {
            setMascota()
            finish()
        }
    }

    private fun setMascota(){
        val intent = Intent()
        intent.putExtra("nombre",etNombre.text.toString())
        intent.putExtra("especie", etEspecie.text.toString())
        intent.putExtra("raza",etRaza.text.toString())
        intent.putExtra("sexo",sexoSeleccionado())
        intent.putExtra("fechaNacimiento",etNacimiento.text.toString())
        intent.putExtra("castrado",cbCastrado.isChecked)

        setResult(Activity.RESULT_OK, intent)
    }

    private fun sexoSeleccionado(): String{
        return when(rgSexo.checkedRadioButtonId){
            R.id.rbFemenino -> "Femenino"
            R.id.rbMasculino -> "Masculino"
            else -> ""
        }
    }

}