package com.example.trainercompanionapp.ui

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.trainercompanionapp.apiObjects.*
import com.example.trainercompanionapp.R
import com.example.trainercompanionapp.adapters.SpriteAdapter
import com.example.trainercompanionapp.adapters.TypeAdapter
import com.skydoves.progressview.ProgressView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokedexSelectionActivity : AppCompatActivity() {
    lateinit var toLanding : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokedex_selection)
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar!!.title ="Pokemon Info"
        val pokemonId = intent.getStringExtra("id")
        toLanding = intent.getStringExtra("toLanding").toString()
        println(pokemonId)


        var pokemonApi = PokemonApi.create().getPokemon(pokemonId.toString())

        pokemonApi.enqueue(object : Callback<Pokemon> {
            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {

                if(response.body()!=null){
                    val pokemon = response.body()
                    if (pokemon != null) {
                        //Name
                        val pokemonName = findViewById<TextView>(R.id.pokemonNameDex)
                        pokemonName.text = pokemon.name.uppercase()
                        //Sprite
                        val pokemonImage = findViewById<ImageView>(R.id.pokemonImageDexDetail)
                        Glide.with(pokemonImage.context)
                            .load(pokemon.getImageUrl())
                            .into(pokemonImage);
                        val animtion = AnimationUtils.loadAnimation(pokemonImage.context, R.anim.selection_dex)
                        pokemonImage.startAnimation(animtion)

                        val sprites = listOf<String>("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/${pokemon.getID()}.png",
                            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/shiny/${pokemon.getID()}.png",
                            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/${pokemon.getID()}.png",
                            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/shiny/${pokemon.getID()}.png",
                            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${pokemon.getID()}.png"

                        )
                        val hp = findViewById<ProgressView>(R.id.hpBar)
                        val atk = findViewById<ProgressView>(R.id.atkBar)
                        val def = findViewById<ProgressView>(R.id.defBar)
                        val spa = findViewById<ProgressView>(R.id.spaBar)
                        val spd = findViewById<ProgressView>(R.id.spdBar)
                        val spe = findViewById<ProgressView>(R.id.speBar)
                        //Stats
                        hp.progress = pokemon.stats[0].base_stat.toFloat()
                        hp.labelText = pokemon.stats[0].base_stat.toString()
                        atk.progress = pokemon.stats[1].base_stat.toFloat()
                        atk.labelText = pokemon.stats[1].base_stat.toString()
                        def.progress = pokemon.stats[2].base_stat.toFloat()
                        def.labelText = pokemon.stats[2].base_stat.toString()
                        spa.progress = pokemon.stats[3].base_stat.toFloat()
                        spa.labelText = pokemon.stats[3].base_stat.toString()
                        spd.progress = pokemon.stats[4].base_stat.toFloat()
                        spd.labelText = pokemon.stats[4].base_stat.toString()
                        spe.progress = pokemon.stats[5].base_stat.toFloat()
                        spe.labelText = pokemon.stats[5].base_stat.toString()
                        //Adapter
                        val recyclerView = findViewById<RecyclerView>(R.id.pokemonTypes)
                        val adapter = TypeAdapter(pokemon.types)
                        recyclerView.adapter = adapter
                        if(pokemon.types.size>1)
                            recyclerView.layoutManager = GridLayoutManager(this@PokedexSelectionActivity, 2)
                        else
                            recyclerView.layoutManager = GridLayoutManager(this@PokedexSelectionActivity, 1)

                        val spriteRecyler = findViewById<RecyclerView>(R.id.pokemonSprites)
                        val spriteAdapter = SpriteAdapter(sprites)
                        spriteRecyler.adapter = spriteAdapter
                        val layoutManager = LinearLayoutManager(this@PokedexSelectionActivity)
                        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
                        spriteRecyler.layoutManager = layoutManager


                    }
                }

                }
            override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                Log.d("Feedback", "Failure to Get Pokémon")
            }
            })

            //getEvolutionChain(pokemonId)




        }


    override fun onSupportNavigateUp(): Boolean {
        val exit = MediaPlayer.create(this,R.raw.exit_outside)
        exit.start()
        onBackPressed()
        if(toLanding.equals("true")){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        return super.onSupportNavigateUp()
    }

    fun getEvolutionChain(pokemonId: Int){
        var evolutionChain : String = "0"
        val pokemonApi = PokemonApi.create().getSpecies(pokemonId.toString())

        pokemonApi.enqueue(object : Callback<Species> {
            override fun onResponse(call: Call<Species>, response: Response<Species>) {
                        if(response.body()!=null){
                            evolutionChain = response.body()!!.evolution_chain.url
                            evolutionChain = evolutionChain.dropLast(1)
                            val chain = evolutionChain.substring(evolutionChain.lastIndexOf('/') +1)
                            getEvolutionSpecies(chain)
                        }
                    }

            override fun onFailure(call: Call<Species>, t: Throwable) {
                Log.d("Feedback", "Failure to Get Pokémon")
            }
        })
    }

    fun getEvolutionSpecies(chainId: String){

        val pokemonApi = PokemonApi.create().getEvolutions(chainId)

        pokemonApi.enqueue(object : Callback<Evolution> {
            override fun onResponse(call: Call<Evolution>, response: Response<Evolution>) {
                if(response.body()!=null){

                    //Log.d("test", getPokemonFromChain(response.body()!!.chain.evolvesTo[0])[0])
                }
            }

            override fun onFailure(call: Call<Evolution>, t: Throwable) {
                Log.d("Feedback", "Failure to Get Pokémon")
            }
        })

    }

    fun getPokemonFromChain(evolvesTo: EvolvesTo): List<String> {
        var pokemonList : List<String> = listOf<String>()
        pokemonList += evolvesTo.species.name
        Log.d("test chain fun", evolvesTo.species.name)
        if(evolvesTo.evolvesTo.isNotEmpty())
            getPokemonFromChain(evolvesTo.evolvesTo[0])
        return pokemonList

    }


}