package com.example.trainercompanionapp.ui

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.trainercompanionapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ExploreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explore)

        val teamBuilder =  findViewById<Button>(R.id.teamBuilderButton)
        val pokedex = findViewById<Button>(R.id.pokedexButton)
        val hallOfFame = findViewById<Button>(R.id.hofButton)
        val shinyDex = findViewById<Button>(R.id.shinyButton)
        val logIn = findViewById<Button>(R.id.logInMain)
        val signOut = findViewById<Button>(R.id.signOutMain)


        // sons a serem usados
        val dexEnterSound = MediaPlayer.create(this, R.raw.enter_pokedex)
        val enter = MediaPlayer.create(this, R.raw.basic_press)
        val logOut = MediaPlayer.create(this, R.raw.exit_outside)


        // adicionar onClickListener para navegar para a actividade correspondente
        teamBuilder.setOnClickListener {
            val intent = Intent(this, TeamBuilderActivity::class.java)
            enter.start()
            startActivity(intent)
        }

        pokedex.setOnClickListener {

            dexEnterSound.start()
            val intent = Intent(this, PokedexActivity::class.java)
            startActivity(intent)

        }

        hallOfFame.setOnClickListener {
            val intent = Intent(this, HallOfFameActivity::class.java)
            enter.start()
            startActivity(intent)

        }

        logIn.setOnClickListener {
            val intent = Intent(this, LogInActivity::class.java)
            enter.start()
            startActivity(intent)
        }

        shinyDex.setOnClickListener {
            val intent = Intent(this, ShinyDexActivity::class.java)
            dexEnterSound.start()
            startActivity(intent)
        }


        //Sign out do user actual
        signOut.setOnClickListener {
            logOut.start()
            Firebase.auth.signOut()
            logIn.visibility = View.VISIBLE
            signOut.visibility = View.INVISIBLE

        }

        //Se houve user signed in, mudar o botão para sign out
        if( FirebaseAuth.getInstance().currentUser != null){
            logIn.visibility = View.INVISIBLE
            signOut.visibility = View.VISIBLE
        }




    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.user_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        val selection = MediaPlayer.create(this, R.raw.basic_press)
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
        if (id == R.id.toStart) {
            selection.start()
            val intent = Intent(this, MainActivity::class.java)
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

}
