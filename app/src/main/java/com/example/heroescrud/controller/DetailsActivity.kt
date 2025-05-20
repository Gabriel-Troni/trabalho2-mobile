package com.example.heroescrud.controller

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.heroescrud.R
import com.example.heroescrud.data.dao.HeroCharDAO
import com.example.heroescrud.model.HeroChar

class DetailsActivity : AppCompatActivity() {
    private lateinit var heroCharDAO: HeroCharDAO
    private var heroCharId: Int = 0
    private lateinit var editTextName: EditText
    private lateinit var editTextCompany: EditText
    private lateinit var editTextOrigin: EditText
    private lateinit var editTextPower: EditText
    private lateinit var buttonDelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_details)
        heroCharDAO = HeroCharDAO(this)
        editTextName = findViewById(R.id.editTextName)
        editTextCompany = findViewById(R.id.editTextCompany)
        editTextOrigin = findViewById(R.id.editTextOrigin)
        editTextPower = findViewById(R.id.editTextPower)
        buttonDelete = findViewById(R.id.buttonDelete)

        heroCharId = intent.getIntExtra("charId", 0)
        println("heroCharId")
        println(heroCharId)
        if (heroCharId != 0) {
            buttonDelete.visibility = Button.VISIBLE
            editChar()
        }
    }

    fun saveChar(view: View) {
        if (editTextName.text.isNotEmpty() && editTextCompany.text.isNotEmpty() && editTextOrigin.text.isNotEmpty()) {
            if (heroCharId == 0) {
                val newChar = HeroChar(
                    name = editTextName.text.toString(),
                    company = editTextCompany.text.toString(),
                    origin = editTextOrigin.text.toString(),
                    power = editTextPower.text.toString()
                )
                heroCharDAO.addHeroChar(newChar)
                Toast.makeText(this, "Personagem adicionado", Toast.LENGTH_SHORT).show()
            } else {
                val updatedChar = HeroChar(
                    id = heroCharId,
                    name = editTextName.text.toString(),
                    company = editTextCompany.text.toString(),
                    origin = editTextOrigin.text.toString(),
                    power = editTextPower.text.toString()
                )
                heroCharDAO.updateHeroChar(updatedChar)
                Toast.makeText(this, "Personagem atualizade", Toast.LENGTH_SHORT).show()
            }
            finish()
        } else {
            Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun editChar() {
        val char = heroCharDAO.getHeroById(heroCharId)
        char?.let {
            editTextName.setText(it.name)
            editTextCompany.setText(it.company)
            editTextOrigin.setText(it.origin)
            editTextPower.setText(it.power)
        }
    }

    fun deleteChar(view: View) {
        heroCharDAO.deleteHeroChar(heroCharId)
        Toast.makeText(this, "Personagem excluído", Toast.LENGTH_SHORT).show()
        finish()
    }

    fun finish(view: View) {
        finish()
    }
}