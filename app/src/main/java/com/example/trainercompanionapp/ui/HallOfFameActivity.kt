package com.example.trainercompanionapp.ui

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trainercompanionapp.R
import com.example.trainercompanionapp.adapters.HofAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HallOfFameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hall_of_fame)

        val addTeam = findViewById<ImageView>(R.id.addTeamButton)
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar!!.title ="Hall of Fame"

        val selection = MediaPlayer.create(this,R.raw.basic_press)
        val animtion = AnimationUtils.loadAnimation(this, R.anim.pokeball_spin)
        addTeam.animation = animtion
        addTeam.setOnClickListener{
            val intent = Intent(this, HallOfFameGames::class.java)

            selection.start()
            startActivity(intent)
        }



        val user = FirebaseAuth.getInstance().currentUser
        if(user != null){
            var hofTeams: Map<String, Map<String, Map<String, String>>>
            val reference = FirebaseDatabase.getInstance("https://trainer-companion-app-default-rtdb.europe-west1.firebasedatabase.app").reference.child(
                FirebaseAuth.getInstance().currentUser!!.uid)
                .child("Hall of Fame")
            reference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot){
                    if(dataSnapshot.value != null){

                        //A informação obtida é um mapa que contém mapas para cada jogo que por sua vez contêm os pokémon
                        hofTeams = dataSnapshot.value as Map<String, Map<String, Map<String, String>>>
                        val recyclerView = findViewById<RecyclerView>(R.id.hofTeams)
                        val adapter = HofAdapter(hofTeams)
                        recyclerView.adapter = adapter

                        recyclerView.layoutManager = GridLayoutManager(this@HallOfFameActivity, 1)

                    }

                }
                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Log.w("Error", "Failed to read value.", error.toException())
                }
            })

        }



    }

    override fun onSupportNavigateUp(): Boolean {
        val exit = MediaPlayer.create(this,R.raw.exit_outside)
        exit.start()
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}