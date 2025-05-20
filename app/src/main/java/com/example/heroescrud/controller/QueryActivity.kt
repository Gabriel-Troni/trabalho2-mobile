package com.example.heroescrud.controller

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.heroescrud.R
import com.example.heroescrud.data.dao.HeroCharDAO
import com.example.heroescrud.model.HeroChar

class QueryActivity : AppCompatActivity() {
    private lateinit var heroCharDAO: HeroCharDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_query)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        heroCharDAO  = HeroCharDAO(this)
    }

    fun queryHeroes(view: View) {
        val editTextQuery = findViewById<EditText>(R.id.editTextQuery)
        val textViewResult = findViewById<TextView>(R.id.textViewResult)
        val heroes = heroCharDAO.getHeroByName(editTextQuery.text.toString())
        println(heroes)
        val result = heroes.joinToString("\n")
        println(result)
        textViewResult.text = result
    }

    fun finish(view: View) {
        finish()
    }
}