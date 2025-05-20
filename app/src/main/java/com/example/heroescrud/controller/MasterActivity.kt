package com.example.heroescrud.controller

import android.app.DownloadManager.Query
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.heroescrud.R
import com.example.heroescrud.data.dao.HeroCharDAO
import com.example.heroescrud.model.HeroChar

class MasterActivity : AppCompatActivity() {
    private lateinit var heroCharDAO: HeroCharDAO
    private lateinit var listView: ListView
    private lateinit var emptyTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_master)
        listView = findViewById(R.id.recyclerViewHeroes)
        emptyTextView = findViewById(R.id.textViewEmptyList)
        heroCharDAO  = HeroCharDAO(this)

        createHeroes()
        listAllChars()

        listView.setOnItemClickListener{ parent, view, position, id ->
            val selectedChar = parent.getItemAtPosition(position) as HeroChar
            val intent = Intent(this, DetailsActivity::class.java).apply {
                putExtra("charId", selectedChar.id)
            }
            startActivity(intent)
        }
    }

    private fun createHeroes() {
        val heroes = heroCharDAO.getAllHeroChars()
        if(heroes.isEmpty()) {
            heroCharDAO.addHeroChar(HeroChar(0, "Batman", "DC", "Gotham", "Dinheiro"))
            heroCharDAO.addHeroChar(HeroChar(0, "Flash", "DC", "Central City", "Super Velocidade"))
            heroCharDAO.addHeroChar(HeroChar(0, "Hulk", "Marvel", "Nova York", "Super Força"))
        }
    }

    private fun listAllChars() {
        val heroChars = heroCharDAO.getAllHeroChars()
        if(heroChars.isEmpty()) {
            listView.visibility = ListView.GONE
            emptyTextView.visibility = TextView.VISIBLE
        } else {
            listView.visibility = ListView.VISIBLE
            emptyTextView.visibility = TextView.GONE
            val adapter: ArrayAdapter<HeroChar> = ArrayAdapter(
                this, android.R.layout.simple_list_item_1, heroChars
            )
            listView.adapter = adapter
        }
    }

    fun newChar(view: View) {
        val intent = Intent(this, DetailsActivity::class.java)
        startActivity(intent)
    }

    fun searchHero(view: View) {
        val intent = Intent(this, QueryActivity::class.java)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        listAllChars()
    }
}