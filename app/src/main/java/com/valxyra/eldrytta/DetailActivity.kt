package com.valxyra.eldrytta

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_hero)

        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        onBackPressedDispatcher.addCallback(this) {
            finish()
        }

        val dataHero = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra("key_hero", Hero::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("key_hero")
        }

        if (dataHero != null) {
            val tvDetailName = findViewById<TextView>(R.id.tv_detail_name)
            val tvDetailHerotitle = findViewById<TextView>(R.id.tv_detail_title)
            val tvDetailRace = findViewById<TextView>(R.id.tv_detail_race)
            val tvDetailJobclass = findViewById<TextView>(R.id.tv_detail_jobclass)
            val tvDetailRegion = findViewById<TextView>(R.id.tv_detail_region)
            val tvDetailDescription = findViewById<TextView>(R.id.tv_detail_description)
            val tvDetailSkill = findViewById<TextView>(R.id.tv_detail_skill)
            val tvDetailEquipment = findViewById<TextView>(R.id.tv_detail_equipment)
            val imgDetailPhoto = findViewById<ImageView>(R.id.img_detail_photo)

            tvDetailName.text = dataHero.name
            tvDetailHerotitle.text = dataHero.herotitle
            tvDetailRace.text = dataHero.race
            tvDetailJobclass.text = dataHero.jobclass
            tvDetailRegion.text = dataHero.region
            tvDetailDescription.text = dataHero.description
            tvDetailSkill.text = dataHero.skill
            tvDetailEquipment.text = dataHero.equipment

            dataHero.photo.let { imgDetailPhoto.setImageResource(it) }
        } else {
            Toast.makeText(this, "Character data not found", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            R.id.action_share -> {
                shareScreenshot()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    @Suppress("DEPRECATION")
    private fun shareScreenshot() {
        val rootView = window.decorView.rootView
        rootView.isDrawingCacheEnabled = true
        val bitmap = Bitmap.createBitmap(rootView.drawingCache)
        rootView.isDrawingCacheEnabled = false

        val tempFile = File(cacheDir, "screenshot.png")
        try {
            val outputStream = FileOutputStream(tempFile)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.close()

            val uri = FileProvider.getUriForFile(
                this,
                "${packageName}.fileprovider",
                tempFile
            )

            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "image/png"
                putExtra(Intent.EXTRA_STREAM, uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            startActivity(Intent.createChooser(shareIntent, "Share Screenshot"))

        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Failed to create a screenshot", Toast.LENGTH_SHORT).show()
        }
    }
}