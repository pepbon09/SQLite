package com.example.basededatossql

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.basededatossql.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val amigosDBHelper = MySQLiteHelper(this)

        binding.btnMostrar.setOnClickListener {
            actualizarDatos(amigosDBHelper)
        }

        binding.btnGuardar.setOnClickListener {
            if (binding.etNombre.text.isNotEmpty() && binding.etEmail.text.isNotEmpty()) {
                var nombre = binding.etNombre.text.toString()
                var email = binding.etEmail.text.toString()
                amigosDBHelper.insertarDatos(nombre,email)
                binding.etNombre.text.clear()
                binding.etEmail.text.clear()
                Toast.makeText(this, "Datos guardados correctamente", Toast.LENGTH_SHORT).show()
                actualizarDatos(amigosDBHelper)
            } else {
                Toast.makeText(this, "Rellena todos los campos primero", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnActualizar.setOnClickListener {
            if ((binding.etIdActualizar.text.isNotEmpty() && binding.etNombreActualizar.text.isNotEmpty()) ||
                (binding.etIdActualizar.text.isNotEmpty() && binding.etEmailActualizar.text.isNotEmpty())) {

                var id = binding.etIdActualizar.text.toString()
                var nombre = binding.etNombreActualizar.text.toString()
                var email = binding.etEmailActualizar.text.toString()

                amigosDBHelper.editarDato(id,nombre, email)

                binding.etIdActualizar.text.clear()
                binding.etNombreActualizar.text.clear()
                binding.etEmailActualizar.text.clear()

                Toast.makeText(this,"Datos actualizados correctamente", Toast.LENGTH_SHORT).show()
                actualizarDatos(amigosDBHelper)
            } else {
                Toast.makeText(this,"Rellena el campo ID y alguno de los dos por lo menos", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnEliminar.setOnClickListener {
            if (binding.etIdBorrar.text.isNotEmpty()) {
                var id = binding.etIdBorrar.text.toString()
                amigosDBHelper.borrarDatos(id)
                binding.etIdBorrar.text.clear()
                Toast.makeText(this,"Datos eliminados correctamente", Toast.LENGTH_SHORT).show()
                actualizarDatos(amigosDBHelper)
            } else {
                Toast.makeText(this,"Rellena el campo primero", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun actualizarDatos(helper: MySQLiteHelper) {
        binding.tvConsulta.text = ""
        val cursor = helper.mostrarDatos()
        if (cursor!!.moveToFirst()) {
            do {
                binding.tvConsulta.append(
                    cursor.getInt(0).toString() + ": ")
                binding.tvConsulta.append(
                    cursor.getString(1).toString()+ ", ")
                binding.tvConsulta.append(
                    cursor.getString(2).toString() + "\n")
            } while (cursor.moveToNext())
        } else {
            binding.tvConsulta.text = "No existe ningun registro"
        }
    }
}