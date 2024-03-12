package com.example.trainercompanionapp.ui

import android.content.Intent
import android.media.MediaPlayer
import android.media.MediaPlayer.OnPreparedListener
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trainercompanionapp.R
import com.example.trainercompanionapp.adapters.LandingAdapter
import com.google.firebase.auth.FirebaseAuth
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    private var aux = true;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dexEnterSound = MediaPlayer.create(this,R.raw.enter_pokedex)
        val enter = MediaPlayer.create(this,R.raw.basic_press)
        //variáveis para os botões
        val videoView = findViewById<VideoView>(R.id.gameVideo);
        val videoButton = findViewById<Button>(R.id.videoButton);
        videoButton.setOnClickListener {
            val intent = Intent(this, PokedexActivity::class.java)
            intent.putExtra("toLanding", "true")
            startActivity(intent)
            dexEnterSound.start()
            finish()
        }



        val exploreButton = findViewById<Button>(R.id.exploreButton)
        exploreButton.setOnClickListener {
            val intent = Intent(this, ExploreActivity::class.java)
            startActivity(intent)
            enter.start()
            finish()
        }

        videoView.setVideoPath("android.resource://" + packageName + "/" + R.raw.intro)

        videoView.requestFocus()
        videoView.start()
        videoView.setOnPreparedListener(OnPreparedListener { mp ->
            mp.isLooping = true
            mp.setVolume(0f,0f)

        })

        val pokemonList = findViewById<RecyclerView>(R.id.randomPokemons)
        val pokemons = generateRandomMons()
        val landingAdapter = LandingAdapter(pokemons)
        pokemonList.adapter = landingAdapter
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        pokemonList.layoutManager= layoutManager


        // sons a serem usados

        val logOut = MediaPlayer.create(this,R.raw.exit_outside)







    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.user_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        val selection = MediaPlayer.create(this,R.raw.basic_press)
        if (id == R.id.about) {
            selection.start()
            val intent = Intent(this, About::class.java)
            startActivity(intent)
        }
        if (id == R.id.author) {
            selection.start()
            val intent = Intent(this, Author::class.java)
            startActivity(intent)
        }
        if (id == R.id.settings) {
            selection.start()
            if(FirebaseAuth.getInstance().currentUser != null)
            {
                val intent = Intent(this, UserSettings::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(applicationContext, "You must be logged in", Toast.LENGTH_SHORT).show()
            }

        }

        return super.onOptionsItemSelected(item)
    }

    private fun generateRandomMons(): List<Int>{
        var list : MutableList<Int> = mutableListOf()
        var random = Random.nextInt(0, 898)
        while (list.size < 10){
            if(!list.contains(random))
                list.add(random)
            else
                random = Random.nextInt(0, 898)
        }
        return list
    }


}