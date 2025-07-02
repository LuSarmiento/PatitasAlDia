package com.lu.patitasaldia.mismascotas

import android.app.Activity
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
import com.lu.patitasaldia.R
import com.lu.patitasaldia.mismascotas.agregarmascota.AgregarMascotaActivity
import com.lu.patitasaldia.mismascotas.Mascota
import kotlinx.coroutines.launch

class MisMascotasActivity : AppCompatActivity() {

    private lateinit var rvMascotas: RecyclerView
    private lateinit var mascotasAdapter: MascotasAdapter

    private lateinit var cvAgregarMascota: CardView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mis_mascotas)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initComponent()
        initListeners()
        initUI()

    }

    override fun onResume() {
        super.onResume()
        val db = AppDatabase.getDatabase(this)
        val mascotaDao = db.mascotaDao()

        lifecycleScope.launch {
            val listaDesdeDB = mascotaDao.obtenerTodas()
            mascotasAdapter.actualizarLista(listaDesdeDB)
        }
    }

    private fun initComponent() {
        rvMascotas = findViewById(R.id.rvMascotas)
        cvAgregarMascota = findViewById(R.id.cvAgregarMascota)
    }

    private fun initUI() {
        rvMascotas.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mascotasAdapter = MascotasAdapter(mutableListOf())
        rvMascotas.adapter = mascotasAdapter

        mascotasAdapter.onItemClicked = { mascota ->
            val intent = Intent(this, DetalleMascotaActivity::class.java)
            intent.putExtra("idMascota", mascota.id)
            startActivity(intent)
        }
    }

    private fun initListeners() {
        cvAgregarMascota.setOnClickListener { navigateToAgregarMascota() }
    }

    private fun navigateToAgregarMascota() {
        val intent = Intent(this, AgregarMascotaActivity::class.java)
        startActivityForResult(intent, 1001)
    }
}