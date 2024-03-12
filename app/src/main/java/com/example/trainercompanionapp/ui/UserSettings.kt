package com.example.trainercompanionapp.ui

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.trainercompanionapp.apiObjects.Pokemon
import com.example.trainercompanionapp.apiObjects.PokemonApi
import com.example.trainercompanionapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserSettings : AppCompatActivity() {
    private var info = mutableMapOf<String, String>()
    var infoList = listOf<EditText>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_settings)

        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar!!.title ="User Settings"



        val pokemonImage = findViewById<ImageView>(R.id.favoriteMonImage)
        val nickname = findViewById<EditText>(R.id.userName)
        val favoriteMon = findViewById<EditText>(R.id.favoriteMon)
        infoList =  listOf<EditText>(nickname,favoriteMon)

        val user = FirebaseAuth.getInstance().currentUser

        var userInfo : Map<String, String>
        // ir buscar a informação do user relativamente à actividade actual
        val reference = FirebaseDatabase.getInstance("https://trainer-companion-app-default-rtdb.europe-west1.firebasedatabase.app").reference.child(FirebaseAuth.getInstance().currentUser!!.uid)
            .child("UserInfo")
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot){
                if(dataSnapshot.value != null){

                    userInfo = dataSnapshot.value as Map<String, String>
                    nickname.setText(userInfo["NickName"])
                    favoriteMon.setText(userInfo["Pokemon"])
                    if(nickname.text.isNotEmpty() and favoriteMon.text.isNotEmpty())
                    addPokemonToUi(favoriteMon, pokemonImage ,favoriteMon.text.toString())

                }

            }
            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("Error", "Failed to read value.", error.toException())
            }
        })

        pokemonImage.setOnClickListener {
            if(favoriteMon.text.toString().isNotEmpty()) {
                addPokemonToUi(favoriteMon, pokemonImage, favoriteMon.text.toString())
                val addPokemonSound = MediaPlayer.create(this, R.raw.mon_add)
                addPokemonSound.start()
            }
            else
                Toast.makeText(applicationContext, "Fill the Name Field please", Toast.LENGTH_SHORT).show()
            }



    }




    fun addPokemonToUi(text: TextView, image: ImageView, pokemonName: String) {
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
                        .into(image);
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
            saveCardInfo(infoList)
            FirebaseDatabase.getInstance("https://trainer-companion-app-default-rtdb.europe-west1.firebasedatabase.app").reference.child(FirebaseAuth.getInstance().currentUser!!.uid)
                .child("UserInfo").updateChildren(info as Map<String, Any>)
        }

        onBackPressed()
        return super.onSupportNavigateUp()
    }

    fun saveCardInfo(infoList : List<EditText>){


            if(infoList != null){
                info["NickName"] = infoList[0]?.text.toString().lowercase()
                info["Pokemon"] = infoList[1]?.text.toString().lowercase()
            }

    }
}