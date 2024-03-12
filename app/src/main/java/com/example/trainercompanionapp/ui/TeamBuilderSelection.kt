package com.example.trainercompanionapp.ui

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.trainercompanionapp.R
import com.example.trainercompanionapp.adapters.TypeAdapter
import com.example.trainercompanionapp.apiObjects.Pokemon
import com.example.trainercompanionapp.apiObjects.PokemonApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TeamBuilderSelection : AppCompatActivity() {
    lateinit var pokemonNick: EditText
    lateinit var slot : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_builder_selection)
        val name = intent.getStringExtra("pokemonName")
        slot = intent.getStringExtra("pokemonSlot").toString()
        val pokemonName = findViewById<TextView>(R.id.pokemonNameDex)
        val pokemonImage = findViewById<ImageView>(R.id.pokemonImageDexDetail)
        pokemonNick = findViewById(R.id.pokemonNickname)
        if (name != null) {
            addPokemonToUi(pokemonName, pokemonImage, name.lowercase())
        }

        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar!!.title ="Team Builder"
        val user = FirebaseAuth.getInstance().currentUser

        if(user != null){

            // ir buscar a informação do user relativamente à actividade actual
            val reference = FirebaseDatabase.getInstance("https://trainer-companion-app-default-rtdb.europe-west1.firebasedatabase.app").reference.child(FirebaseAuth.getInstance().currentUser!!.uid)
                .child("Team").child("Nickname $slot")
            reference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot){
                    if(dataSnapshot.value != null){
                        println("enter")
                        val pokemonNickName = dataSnapshot.value as String
                        pokemonNick.setText(pokemonNickName)
                        // se o pokémon existir, é adicionado à lista actual

                    }

                }
                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Log.w("Error", "Failed to read value.", error.toException())
                }
            })

        }




    }

    private fun addPokemonToUi(text: TextView, image: ImageView, pokemonName: String) {
        val pokemonApi = PokemonApi.create().getPokemon(pokemonName)

        pokemonApi.enqueue(object : Callback<Pokemon> {
            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                Log.d("Pokemon", "Success")
                if(response.body() != null){

                    var name: String = response.body()!!.name
                    var url : String = response.body()!!.getOfficialArtwork()
                    text.text = name.uppercase()
                    Glide.with(image.context)
                        .load(url)
                        .into(image)
                    val recyclerView = findViewById<RecyclerView>(R.id.pokemonTypes)
                    val adapter = TypeAdapter(response.body()!!.types)
                    recyclerView.adapter = adapter
                    if(response.body()!!.types.size>1)
                        recyclerView.layoutManager = GridLayoutManager(this@TeamBuilderSelection, 2)
                    else
                        recyclerView.layoutManager = GridLayoutManager(this@TeamBuilderSelection, 1)
                }
                else
                    Toast.makeText(applicationContext, "Invalid Name or Id", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                Log.d("Pokemon", "Failed")
            }
        }
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        val saveSound = MediaPlayer.create(this,R.raw.save)
        saveSound.start()
        if(FirebaseAuth.getInstance().currentUser!=null)
        {
            //Sallvar a informação toda na lista pokemonList, enviar esta lista para a Database na àrvore associada ao User
            FirebaseDatabase.getInstance("https://trainer-companion-app-default-rtdb.europe-west1.firebasedatabase.app").reference.child(
                FirebaseAuth.getInstance().currentUser!!.uid)
                .child("Team").child("NickName $slot").setValue(pokemonNick.text.toString())
        }

        onBackPressed()
        return super.onSupportNavigateUp()
    }
}