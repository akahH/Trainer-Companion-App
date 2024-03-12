package com.example.trainercompanionapp.ui

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
import com.example.trainercompanionapp.apiObjects.Pokemon
import com.example.trainercompanionapp.apiObjects.PokemonApi
import com.example.trainercompanionapp.apiObjects.PokemonList
import com.example.trainercompanionapp.R
import com.example.trainercompanionapp.adapters.ShinyDexAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShinyDexActivity : AppCompatActivity() {
        var caughtShiny : MutableMap<String, String> = mutableMapOf<String, String>()
        var limit: Int = 898
        var offset:Int = 0
        var adapter : ShinyDexAdapter = ShinyDexAdapter(listOf(),0, mutableMapOf())
    lateinit var  title: TextView
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_shiny_dex)

            val actionBar = supportActionBar
            actionBar!!.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title ="Shiny Dex"

            getShinyDex()



        }

        override fun onSupportNavigateUp(): Boolean {
            val exit = MediaPlayer.create(this,R.raw.exit_dex)
            if(FirebaseAuth.getInstance().currentUser!=null)
                exit.start()
                FirebaseDatabase.getInstance("https://trainer-companion-app-default-rtdb.europe-west1.firebasedatabase.app").reference.child(
                    FirebaseAuth.getInstance().currentUser!!.uid)
                    .child("Shiny Dex").updateChildren(caughtShiny as Map<String, Any>)

            onBackPressed()
            onBackPressed()
            return super.onSupportNavigateUp()
        }

        override fun onCreateOptionsMenu(menu: Menu): Boolean {

            menuInflater.inflate(R.menu.shiny_dex_menu, menu)
            val searchItem= menu.findItem(R.id.search)
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
            if (id == R.id.filter_shiny_dex) {
                selection.start()
                filterShinyDex()
            }
            if (id == R.id.unfilter_shiny_dex) {
                selection.start()
                getShinyDex()
            }

            return super.onOptionsItemSelected(item)
        }

    private fun getShinyDex(){
        val pokemonApi = PokemonApi.create().getListPokemon(limit, offset)
        val user = FirebaseAuth.getInstance().currentUser
        if(user != null){
            //Se existir user, ir buscar a sua lista de shiny Dex
            val reference = FirebaseDatabase.getInstance("https://trainer-companion-app-default-rtdb.europe-west1.firebasedatabase.app").reference.child(FirebaseAuth.getInstance().currentUser!!.uid)
                .child("Shiny Dex")
            reference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.value != null) {

                        caughtShiny = dataSnapshot.value as MutableMap<String, String>

                        pokemonApi.clone().enqueue(object : Callback<PokemonList> {
                            override fun onResponse(call: Call<PokemonList>, response: Response<PokemonList>) {

                                if(response.body()!=null){
                                    Log.d("Test", "Success")

                                    val recyclerView = findViewById<RecyclerView>(R.id.shinyDexList)
                                    adapter = ShinyDexAdapter(
                                        response.body()!!.results,
                                        offset,
                                        caughtShiny
                                    )
                                    recyclerView.adapter = adapter

                                    recyclerView.layoutManager = GridLayoutManager(this@ShinyDexActivity, 2)
                                }


                            }

                            override fun onFailure(call: Call<PokemonList>, t: Throwable) {
                                Log.d("Pokemon", "Failed")
                            }
                        }
                        )


                    }
                    else{
                        pokemonApi.enqueue(object : Callback<PokemonList> {
                            override fun onResponse(call: Call<PokemonList>, response: Response<PokemonList>) {

                                if(response.body()!=null){
                                    Log.d("Test", "Success")

                                    val recyclerView = findViewById<RecyclerView>(R.id.shinyDexList)
                                    adapter = ShinyDexAdapter(
                                        response.body()!!.results,
                                        offset,
                                        caughtShiny
                                    )
                                    recyclerView.adapter = adapter

                                    recyclerView.layoutManager = GridLayoutManager(this@ShinyDexActivity, 2)
                                }

                            }

                            override fun onFailure(call: Call<PokemonList>, t: Throwable) {
                                Log.d("Pokemon", "Failed")
                            }
                        }
                        )
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Log.w("Error", "Failed to read value.", error.toException())
                }
            })

        }




        else{

            pokemonApi.enqueue(object : Callback<PokemonList> {
                override fun onResponse(call: Call<PokemonList>, response: Response<PokemonList>) {

                    if(response.body()!=null){
                        Log.d("Test", "Success")

                        val recyclerView = findViewById<RecyclerView>(R.id.shinyDexList)
                        adapter = ShinyDexAdapter(
                            response.body()!!.results,
                            offset,
                            caughtShiny
                        )
                        recyclerView.adapter = adapter

                        recyclerView.layoutManager = GridLayoutManager(this@ShinyDexActivity, 2)
                    }

                }

                override fun onFailure(call: Call<PokemonList>, t: Throwable) {
                    Log.d("Pokemon", "Failed")
                }
            }
            )
        }
    }

    private fun filterShinyDex(){


        val pokemonApi = PokemonApi.create().getListPokemon(limit, offset)


        val reference = FirebaseDatabase.getInstance("https://trainer-companion-app-default-rtdb.europe-west1.firebasedatabase.app").reference.child(FirebaseAuth.getInstance().currentUser!!.uid)
            .child("Shiny Dex")
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.value != null) {


                    caughtShiny = dataSnapshot.value as MutableMap<String, String>
                    //Filtra lista em que os valores comecem por C, "Caught"
                    val onlyShiny = caughtShiny.filter { (_, value ) ->
                        value.startsWith("C")
                    }

                    pokemonApi.clone().enqueue(object : Callback<PokemonList> {
                        override fun onResponse(call: Call<PokemonList>, response: Response<PokemonList>) {

                            if(response.body()!=null){
                                Log.d("Test", "Success")

                                //Verificar do pokédex qual dos pokémon se encontra na lista de caught
                                val filteredPokemonList :MutableList<Pokemon> = mutableListOf()
                                for(pokemon in response.body()!!.results){
                                    if(onlyShiny.contains(pokemon.name)){
                                        filteredPokemonList.add(pokemon)
                                    }


                                }

                                val recyclerView = findViewById<RecyclerView>(R.id.shinyDexList)
                                adapter = ShinyDexAdapter(filteredPokemonList, offset, caughtShiny)
                                recyclerView.adapter = adapter

                                recyclerView.layoutManager = GridLayoutManager(this@ShinyDexActivity, 2)
                            }

                        }

                        override fun onFailure(call: Call<PokemonList>, t: Throwable) {
                            Log.d("Pokemon", "Failed")
                        }
                    }
                    )


                }
            }
            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("Error", "Failed to read value.", error.toException())
            }
        })

    }
    }
