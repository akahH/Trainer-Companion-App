package com.example.trainercompanionapp.ui

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.example.trainercompanionapp.R

class HallOfFameGames : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hall_of_fame_games)

        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar!!.title ="Hall of Fame : Game Selection"

        //A classe faz extend a onclickListener, daí só é necessário fazer override ao método
        //para ficar aplicado a qualquer elemento desta classe
        val yellow = findViewById<ImageView>(R.id.yellow)
        yellow.setOnClickListener(this)
        val red = findViewById<ImageView>(R.id.red)
        red.setOnClickListener(this)
        val blue = findViewById<ImageView>(R.id.blue)
        blue.setOnClickListener(this)
        val silver = findViewById<ImageView>(R.id.silver)
        silver.setOnClickListener(this)
        val gold = findViewById<ImageView>(R.id.gold)
        gold.setOnClickListener(this)
        val crystal = findViewById<ImageView>(R.id.crystal)
        crystal.setOnClickListener(this)
        val ruby = findViewById<ImageView>(R.id.ruby)
        ruby.setOnClickListener(this)
        val saphire = findViewById<ImageView>(R.id.saphire)
        saphire.setOnClickListener(this)
        val emerald = findViewById<ImageView>(R.id.emerald)
        emerald.setOnClickListener(this)
        val pearl = findViewById<ImageView>(R.id.pearl)
        pearl.setOnClickListener(this)
        val diamond = findViewById<ImageView>(R.id.diamond)
        diamond.setOnClickListener(this)
        val platinum = findViewById<ImageView>(R.id.platinum)
        platinum.setOnClickListener(this)
        val white = findViewById<ImageView>(R.id.white)
        white.setOnClickListener(this)
        val black = findViewById<ImageView>(R.id.black)
        black.setOnClickListener(this)
        val white2 = findViewById<ImageView>(R.id.white2)
        white2.setOnClickListener(this)
        val black2 = findViewById<ImageView>(R.id.black2)
        black2.setOnClickListener(this)
        val sun = findViewById<ImageView>(R.id.sun)
        sun.setOnClickListener(this)
        val moon = findViewById<ImageView>(R.id.moon)
        moon.setOnClickListener(this)
        val ultraSun = findViewById<ImageView>(R.id.ultraSun)
        ultraSun.setOnClickListener(this)
        val ultraMoon = findViewById<ImageView>(R.id.ultraMoon)
        ultraMoon.setOnClickListener(this)
        val sword = findViewById<ImageView>(R.id.sword)
        sword.setOnClickListener(this)
        val shield = findViewById<ImageView>(R.id.shield)
        shield.setOnClickListener(this)
    }

    //TODO menu for custom games

    override fun onSupportNavigateUp(): Boolean {
        val exit = MediaPlayer.create(this,R.raw.exit_outside)
        exit.start()
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onClick(v: View?) {
        val selection = MediaPlayer.create(this,R.raw.basic_press)
        when(v!!.id)
        {
            R.id.yellow ->{
                val intent = Intent(this, HallOfFameTeam::class.java)
                intent.putExtra("gameImage", R.drawable.yellow)
                intent.putExtra("gameName", "Yellow")
                selection.start()
                startActivity(intent)
            }

            R.id.red ->{
                val intent = Intent(this, HallOfFameTeam::class.java)
                intent.putExtra("gameImage", R.drawable.red)
                intent.putExtra("gameName", "Red")
                selection.start()
                startActivity(intent)
            }
            R.id.blue ->{
                val intent = Intent(this, HallOfFameTeam::class.java)
                intent.putExtra("gameImage", R.drawable.blue)
                intent.putExtra("gameName", "Blue")
                selection.start()
                startActivity(intent)
            }
            R.id.silver ->{
                val intent = Intent(this, HallOfFameTeam::class.java)
                intent.putExtra("gameImage", R.drawable.silver)
                intent.putExtra("gameName", "Silver")
                selection.start()
                startActivity(intent)
            }
            R.id.gold ->{
                val intent = Intent(this, HallOfFameTeam::class.java)
                intent.putExtra("gameImage", R.drawable.gold)
                intent.putExtra("gameName", "Gold")
                selection.start()
                startActivity(intent)
            }
            R.id.crystal ->{
                val intent = Intent(this, HallOfFameTeam::class.java)
                intent.putExtra("gameImage", R.drawable.crystal)
                intent.putExtra("gameName", "Crystal")
                selection.start()
                startActivity(intent)
            }
            R.id.ruby ->{
                val intent = Intent(this, HallOfFameTeam::class.java)
                intent.putExtra("gameImage", R.drawable.ruby)
                intent.putExtra("gameName", "Ruby")
                selection.start()
                startActivity(intent)
            }
            R.id.saphire ->{
                val intent = Intent(this, HallOfFameTeam::class.java)
                intent.putExtra("gameImage", R.drawable.sapphire)
                intent.putExtra("gameName", "Sapphire")
                selection.start()
                startActivity(intent)
            }
            R.id.emerald ->{
                val intent = Intent(this, HallOfFameTeam::class.java)
                intent.putExtra("gameImage", R.drawable.emerald)
                intent.putExtra("gameName", "Emerald")
                selection.start()
                startActivity(intent)
            }
            R.id.pearl ->{
                val intent = Intent(this, HallOfFameTeam::class.java)
                intent.putExtra("gameImage", R.drawable.pearl)
                intent.putExtra("gameName", "Pearl")
                selection.start()
                startActivity(intent)
            }
            R.id.diamond ->{
                val intent = Intent(this, HallOfFameTeam::class.java)
                intent.putExtra("gameImage", R.drawable.diamond)
                intent.putExtra("gameName", "Diamond")
                selection.start()
                startActivity(intent)
            }
            R.id.platinum ->{
                val intent = Intent(this, HallOfFameTeam::class.java)
                intent.putExtra("gameImage", R.drawable.platinum)
                intent.putExtra("gameName", "Platinum")
                selection.start()
                startActivity(intent)
            }
            R.id.white ->{
                val intent = Intent(this, HallOfFameTeam::class.java)
                intent.putExtra("gameImage", R.drawable.white)
                intent.putExtra("gameName", "White")
                selection.start()
                startActivity(intent)
            }
            R.id.black ->{
                val intent = Intent(this, HallOfFameTeam::class.java)
                intent.putExtra("gameImage", R.drawable.black)
                intent.putExtra("gameName", "Black")
                selection.start()
                startActivity(intent)
            }
            R.id.white2 ->{
                val intent = Intent(this, HallOfFameTeam::class.java)
                intent.putExtra("gameImage", R.drawable.white_two)
                intent.putExtra("gameName", "White 2")
                selection.start()
                startActivity(intent)
            }
            R.id.black2 ->{
                val intent = Intent(this, HallOfFameTeam::class.java)
                intent.putExtra("gameImage", R.drawable.black_two)
                intent.putExtra("gameName", "Black 2")
                selection.start()
                startActivity(intent)
            }
            R.id.sun ->{
                val intent = Intent(this, HallOfFameTeam::class.java)
                intent.putExtra("gameImage", R.drawable.sun)
                intent.putExtra("gameName", "Sun")
                selection.start()
                startActivity(intent)
            }
            R.id.moon ->{
                val intent = Intent(this, HallOfFameTeam::class.java)
                intent.putExtra("gameImage", R.drawable.moon)
                intent.putExtra("gameName", "Moon")
                selection.start()
                startActivity(intent)
            }
            R.id.ultraSun ->{
                val intent = Intent(this, HallOfFameTeam::class.java)
                intent.putExtra("gameImage", R.drawable.ultra_sun)
                intent.putExtra("gameName", "Ultra Sun")
                selection.start()
                startActivity(intent)
            }
            R.id.ultraMoon ->{
                val intent = Intent(this, HallOfFameTeam::class.java)
                intent.putExtra("gameImage", R.drawable.ultra_moon)
                intent.putExtra("gameName", "Ultra Moon")
                selection.start()
                startActivity(intent)
            }
            R.id.sword ->{
                val intent = Intent(this, HallOfFameTeam::class.java)
                intent.putExtra("gameImage", R.drawable.sword)
                intent.putExtra("gameName", "Sword")
                selection.start()
                startActivity(intent)
            }
            R.id.shield ->{
                val intent = Intent(this, HallOfFameTeam::class.java)
                intent.putExtra("gameImage", R.drawable.shield)
                intent.putExtra("gameName", "Shield")
                selection.start()
                startActivity(intent)
            }
        }
    }
}