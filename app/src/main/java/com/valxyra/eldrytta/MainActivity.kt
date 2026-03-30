package com.valxyra.eldrytta

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var rvHeroes: RecyclerView
    private val list = ArrayList<Hero>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val scrollLayout = findViewById<NestedScrollView>(R.id.scrollLayout)
        scrollLayout.post {
            scrollLayout.scrollTo(0, 0)
        }

        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        supportActionBar?.setDisplayShowTitleEnabled(false)

        rvHeroes = findViewById(R.id.rv_heroes)
        rvHeroes.setHasFixedSize(true)
        list.addAll(getListHeroes())
        showRecyclerList()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.about_page -> {
                val intent = Intent(this, AboutPage::class.java)
                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getListHeroes(): ArrayList<Hero> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataHerotitle = resources.getStringArray(R.array.data_herotitle)
        val dataDescription = resources.getStringArray(R.array.data_description)
        val dataRace = resources.getStringArray(R.array.data_race)
        val dataJobclass = resources.getStringArray(R.array.data_jobclass)
        val dataRegion = resources.getStringArray(R.array.data_region)
        val dataSkill = resources.getStringArray(R.array.data_skill)
        val dataEquipment = resources.getStringArray(R.array.data_equipment)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
        val listHero = ArrayList<Hero>()
        for (i in dataName.indices) {
            val hero = Hero(
                dataName[i],
                dataHerotitle[i],
                dataDescription[i],
                dataRace[i],
                dataJobclass[i],
                dataRegion[i],
                dataSkill[i],
                dataEquipment[i],
                dataPhoto.getResourceId(i, -1)
            )
            listHero.add(hero)
        }
        return listHero
    }

    private fun showRecyclerList() {
        rvHeroes.layoutManager = LinearLayoutManager(this)
        val listHeroAdapter = ListHeroAdapter(list)
        rvHeroes.adapter = listHeroAdapter

        listHeroAdapter.setOnItemClickCallback(object : ListHeroAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Hero) {
                showSelectedHero(data)
            }
        })
    }

    private fun showSelectedHero(hero: Hero) {
        Toast.makeText(this, "You choose " + hero.name, Toast.LENGTH_SHORT).show()
    }

}