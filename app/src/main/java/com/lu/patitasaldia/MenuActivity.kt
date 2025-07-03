package com.lu.patitasaldia

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.lu.patitasaldia.historialmedico.HistorialMedicoActivity
import com.lu.patitasaldia.mismascotas.MisMascotasActivity

class MenuActivity : AppCompatActivity() {

    private lateinit var cvMisMascotas: CardView
    private lateinit var cvHistorialMedico: CardView
    private lateinit var fabAgregarPendiente: FloatingActionButton

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

    }

    private fun initComponents() {
        cvMisMascotas = findViewById(R.id.cvMisMascotas)
        cvHistorialMedico = findViewById(R.id.cvHistorialMedico)
        fabAgregarPendiente = findViewById(R.id.fabAgregarPendiente)
    }

    private fun initListeners() {
        cvMisMascotas.setOnClickListener { navigateToMisMascotas() }
        cvHistorialMedico.setOnClickListener { navigateToHistorialMedico() }

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