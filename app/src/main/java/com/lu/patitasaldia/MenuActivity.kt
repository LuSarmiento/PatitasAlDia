package com.lu.patitasaldia

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.lu.patitasaldia.calendario.CalendarioActivity
import com.lu.patitasaldia.historialmedico.HistorialMedicoActivity
import com.lu.patitasaldia.mismascotas.MisMascotasActivity

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val cvMisMascotas = findViewById<CardView>(R.id.cvMisMascotas)
        val cvCalendario = findViewById<CardView>(R.id.cvCalendario)
        val cvHistorialMedico = findViewById<CardView>(R.id.cvHistorialMedico)


        cvMisMascotas.setOnClickListener { navigateToMisMascotas() }
        cvCalendario.setOnClickListener { navigateToCalendario() }
        cvHistorialMedico.setOnClickListener { navigateToHistorialMedico() }
    }

    private fun navigateToMisMascotas(){
        val intent = Intent(this, MisMascotasActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToCalendario(){
        val intent = Intent(this, CalendarioActivity::class.java)
        startActivity(intent)

    }

    private fun navigateToHistorialMedico(){
        val intent = Intent(this, HistorialMedicoActivity::class.java)
        startActivity(intent)

    }






}