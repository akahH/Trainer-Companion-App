package com.example.trainercompanionapp.ui

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trainercompanionapp.apiObjects.PokemonApi
import com.example.trainercompanionapp.apiObjects.PokemonList
import com.example.trainercompanionapp.R
import com.example.trainercompanionapp.adapters.PokedexAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokedexActivity : AppCompatActivity() {
    var limit: Int = 0
    var offset:Int = 0
    lateinit var  title: TextView
    lateinit var  toLanding : String
    var adapter : PokedexAdapter = PokedexAdapter(listOf(),0)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokedex)
        val gen = intent.getIntExtra("gen", 0)
        toLanding = intent.getStringExtra("toLanding").toString()
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar!!.title ="National Dex"
        title = findViewById(R.id.dexDetail)


        selectDexLimits(gen)
        val pokemonApi = PokemonApi.create().getListPokemon(limit, offset)

        pokemonApi.enqueue(object : Callback<PokemonList> {
            override fun onResponse(call: Call<PokemonList>, response: Response<PokemonList>) {

                if(response.body()!=null){
                    Log.d("Test", "Success")

                    //RecyclerView e o seu adaptador
                    val recyclerView = findViewById<RecyclerView>(R.id.pokedexList)
                    adapter = PokedexAdapter(response.body()!!.results, offset)
                    recyclerView.adapter = adapter

                    //Vista em grelha de duas colunas
                    recyclerView.layoutManager = GridLayoutManager(this@PokedexActivity, 2)
                }

            }

            override fun onFailure(call: Call<PokemonList>, t: Throwable) {
                Log.d("Pokemon", "Failed")
            }
        }
        )


    }

    override fun onSupportNavigateUp(): Boolean {
        val exitsound = MediaPlayer.create(this,R.raw.exit_dex)
        exitsound.start()
        onBackPressed()
        if(toLanding.equals("true")){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.pokedex_menu, menu)
        val searchItem= menu.findItem(R.id.search)
        //Operações para aceder à searchView na action bar e filtrar a lista
        if(searchItem!=null){
            val searchView= searchItem.actionView as SearchView
            searchView.setOnQueryTextListener( object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    adapter.filter.filter(newText)

                    return false
                }

            })
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        val selection = MediaPlayer.create(this,R.raw.basic_press)
        if (id == R.id.filter_dex) {
            selection.start()
            val intent = Intent(this, SelectDexActivity::class.java)
            startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    //Filtragem do Pokédex, os limites e offsets estão definidos com os pokémon correspondentes a cada geração
    fun selectDexLimits(gen : Int){
        when(gen){
            0->{
                limit = 898
                offset = 0

            }
            1->{
                limit = 151
                offset = 0
                title.text = "FIRST GEN DEX"
            }

            2->{
                limit = 99
                offset = 151
                title.text = "SECOND GEN DEX"
            }
            3->{
                limit = 135
                offset = 251
                title.text = "THIRD GEN DEX"
            }
            4->{
                limit = 108
                offset = 386
                title.text = "FORTH GEN DEX"
            }
            5->{
                limit = 155
                offset = 494
                title.text = "FIFTH GEN DEX"
            }
            6->{
                limit = 72
                offset = 649
                title.text = "SIXTH GEN DEX"
            }
            7->{
                limit = 88
                offset = 721
                title.text = "SEVENTH GEN DEX"
            }
            8->{
                limit = 89
                offset = 809
                title.text = "EIGHT GEN DEX"
            }
        }
    }
}