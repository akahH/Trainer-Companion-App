package com.example.trainercompanionapp.ui

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.animation.AnimationUtils
import android.widget.*
import com.bumptech.glide.Glide
import com.example.trainercompanionapp.apiObjects.Pokemon
import com.example.trainercompanionapp.apiObjects.PokemonApi
import com.example.trainercompanionapp.R
import com.google.android.material.card.MaterialCardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HallOfFameTeam : AppCompatActivity() {
    // Nome do Jogo da Equipa Actual
    lateinit var gameName:String

    //Lista dos objectos que conteêm a informação de pokémon
    var pokemonList = listOf<MaterialCardView>()

    //mapa a ser enviado para a FirebaDatabase
    private var team = mutableMapOf<String, Map<String, String>>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hall_of_fame_team)
        //equipa a ser recebida da actividade anterior se já existir
        val team = intent.getStringArrayListExtra("team")
        //Imagem do jogo actual a ser recebida da actividade anterior
        val imageResource:Int = intent.getIntExtra("gameImage",0)
        //Nome do jogo actual a ser recebida da actividade anterior
        gameName= intent.getStringExtra("gameName").toString()
        //Atribuir a imagem
        val gameImage = findViewById<ImageView>(R.id.gameImage)
        gameImage.setImageResource(imageResource)

        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar!!.title ="Team Builder"

        val pokemon1 = findViewById<MaterialCardView>(R.id.addPokemonHof1)
        val pokemon2 = findViewById<MaterialCardView>(R.id.addPokemonHof2)
        val pokemon3 = findViewById<MaterialCardView>(R.id.addPokemonHof3)
        val pokemon4 = findViewById<MaterialCardView>(R.id.addPokemonHof4)
        val pokemon5 = findViewById<MaterialCardView>(R.id.addPokemonHof5)
        val pokemon6 = findViewById<MaterialCardView>(R.id.addPokemonHof6)
        pokemonList = listOf<MaterialCardView>(pokemon1, pokemon2, pokemon3,
        pokemon4, pokemon5, pokemon6)

        setOnClickForCards(pokemonList)

        if(team != null)
            fillTeam(team, pokemonList)

    }

    fun addPokemonToUi(text: TextView, image: ImageView, id:TextView, pokemonName: String) {
        val pokemonApi = PokemonApi.create().getPokemon(pokemonName)

        pokemonApi.enqueue(object : Callback<Pokemon> {
            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                Log.d("Pokemon", "Success")
                if(response.body() != null){

                    var name: String = response.body()!!.name
                    var url : String = response.body()!!.getImageUrl()
                    val pokemonId : String = response.body()!!.getID()
                    text.text = name.uppercase()
                    id.text = pokemonId
                    Log.d("Test", pokemonId)
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
    fun setOnClickForCards(cards : List<MaterialCardView>){
        val selection = MediaPlayer.create(this,R.raw.mon_add)
        val animtion = AnimationUtils.loadAnimation(this, R.anim.bounce_anim)

        for(card in cards){
            card.setOnClickListener{

                val name = card.findViewById<EditText>(R.id.pokemonNameTeam)
                val image = card.findViewById<ImageView>(R.id.pokemonImageTeam)
                val pokemonId = card.findViewById<TextView>(R.id.pokemonId)
                image.startAnimation(animtion)
                Log.d("name", name.text.toString())
                if(name.text.toString().isNotEmpty()){
                    addPokemonToUi(name, image ,pokemonId, name.text.toString())
                    selection.start()

                }

                else
                    Toast.makeText(applicationContext, "Fill the Name Field please", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //Preenche a equipa se esta já existir
    fun fillTeam(team : ArrayList<String>, cards : List<MaterialCardView>){
        for(i in 0 until team.size){

                val name = cards[i].findViewById<EditText>(R.id.pokemonNameTeam)
                val image = cards[i].findViewById<ImageView>(R.id.pokemonImageTeam)
                val pokemonId = cards[i].findViewById<TextView>(R.id.pokemonId)
                addPokemonToUi(name, image ,pokemonId, team[i])

        }
    }

    fun saveCardInfo(cards : List<MaterialCardView>){
        for ((aux, card) in cards.withIndex()){
            val name = card.findViewById<EditText>(R.id.pokemonNameTeam)
            val pokemonId = card.findViewById<TextView>(R.id.pokemonId)
            if(name.text.toString().isNotEmpty()){
                team["Pokemon$aux"] = mapOf("Id" to pokemonId.text.toString(),
                "Name" to name.text.toString())
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val save = MediaPlayer.create(this,R.raw.exit_outside)
        save.start()
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.hof_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        val save = MediaPlayer.create(this,R.raw.add_team)
        if (id == R.id.save_team) {
            save.start()
            saveCardInfo(pokemonList)
            if(FirebaseAuth.getInstance().currentUser!=null)
                FirebaseDatabase.getInstance("https://trainer-companion-app-default-rtdb.europe-west1.firebasedatabase.app").reference.child(
                    FirebaseAuth.getInstance().currentUser!!.uid)
                    .child("Hall of Fame").child("Pokemon $gameName").updateChildren(team as Map<String, Any>)

            finish()
        }
        return super.onOptionsItemSelected(item)
    }




}