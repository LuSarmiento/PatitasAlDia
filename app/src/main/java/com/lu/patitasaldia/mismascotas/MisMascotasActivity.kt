package com.lu.patitasaldia.mismascotas

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lu.patitasaldia.R
import com.lu.patitasaldia.mismascotas.agregarmascota.AgregarMascotaActivity
import com.lu.patitasaldia.mismascotas.agregarmascota.Mascota

class MisMascotasActivity : AppCompatActivity() {

    val listaMascotas = mutableListOf<Mascota>()

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1001 && resultCode == Activity.RESULT_OK && data != null) {
            val nuevaMascota = Mascota(
                nombre = data.getStringExtra("nombre") ?: "",
                especie = data.getStringExtra("especie") ?: "",
                raza = data.getStringExtra("raza") ?: "",
                fechaNacimiento = data.getStringExtra("fechaNacimiento") ?: "",
                sexo = data.getStringExtra("sexo") ?: "",
                castrado = data.getBooleanExtra("castrado", false)
            )

            listaMascotas.add(nuevaMascota)
            mascotasAdapter.notifyItemInserted(listaMascotas.size - 1)
        }
    }

    private fun initComponent() {

        rvMascotas = findViewById(R.id.rvMascotas)
        cvAgregarMascota = findViewById(R.id.cvAgregarMascota)
    }

    private fun initUI() {
        mascotasAdapter = MascotasAdapter(listaMascotas)
        rvMascotas.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvMascotas.adapter = mascotasAdapter
    }

    private fun initListeners() {
        cvAgregarMascota.setOnClickListener { navigateToAgregarMascota() }
    }

    private fun navigateToAgregarMascota() {
        val intent = Intent(this, AgregarMascotaActivity::class.java)
        startActivityForResult(intent, 1001)
    }
}