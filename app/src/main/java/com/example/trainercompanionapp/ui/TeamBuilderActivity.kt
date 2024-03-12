package com.example.trainercompanionapp.ui

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.view.menu.MenuView
import com.bumptech.glide.Glide
import com.example.trainercompanionapp.apiObjects.Pokemon
import com.example.trainercompanionapp.apiObjects.PokemonApi
import com.example.trainercompanionapp.R
import com.google.android.material.card.MaterialCardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TeamBuilderActivity : AppCompatActivity() {

    //Lista dos objectos que conteêm a informação de pokémon
    var pokemonList = listOf<MaterialCardView>()
    var lock : Boolean = false

    private var team = mutableMapOf<String, String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_builder)

        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar!!.title ="Team Builder"

        //User actual logged in
        val user = FirebaseAuth.getInstance().currentUser



        //cards que contêm informação dos pokémopn
        val pokemon1 = findViewById<MaterialCardView>(R.id.addTeam1)
        val pokemon2 = findViewById<MaterialCardView>(R.id.addTeam2)
        val pokemon3 = findViewById<MaterialCardView>(R.id.addTeam3)
        val pokemon4 = findViewById<MaterialCardView>(R.id.addTeam4)
        val pokemon5 = findViewById<MaterialCardView>(R.id.addTeam5)
        val pokemon6 = findViewById<MaterialCardView>(R.id.addTeam6)
        pokemonList = listOf<MaterialCardView>(pokemon1, pokemon2, pokemon3,
            pokemon4, pokemon5, pokemon6)


        if(user != null){
            var userTeam : Map<String, String> = mapOf()
            // ir buscar a informação do user relativamente à actividade actual
         val reference = FirebaseDatabase.getInstance("https://trainer-companion-app-default-rtdb.europe-west1.firebasedatabase.app").reference.child(FirebaseAuth.getInstance().currentUser!!.uid)
                .child("Team")
            reference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot){
                    if(dataSnapshot.value != null){

                        userTeam = dataSnapshot.value as Map<String, String>
                        // se o pokémon existir, é adicionado à lista actual
                        if(userTeam["Pokemon0"] !=null)
                            addPokemonToUi(pokemon1.findViewById<EditText>(R.id.pokemonNameTeam),
                                pokemon1.findViewById<ImageView>(R.id.pokemonImageTeam),
                                userTeam["Pokemon0"]!!)
                        if(userTeam["Pokemon1"] !=null)
                            addPokemonToUi(pokemon2.findViewById<EditText>(R.id.pokemonNameTeam),
                                pokemon2.findViewById<ImageView>(R.id.pokemonImageTeam),
                                userTeam["Pokemon1"]!!)
                        if(userTeam["Pokemon2"] !=null)
                            addPokemonToUi(pokemon3.findViewById<EditText>(R.id.pokemonNameTeam),
                                pokemon3.findViewById<ImageView>(R.id.pokemonImageTeam),
                                userTeam["Pokemon2"]!!)
                        if(userTeam["Pokemon3"] !=null)
                            addPokemonToUi(pokemon4.findViewById<EditText>(R.id.pokemonNameTeam),
                                pokemon4.findViewById<ImageView>(R.id.pokemonImageTeam),
                                userTeam["Pokemon3"]!!)
                        if(userTeam["Pokemon4"] !=null)
                            addPokemonToUi(pokemon5.findViewById<EditText>(R.id.pokemonNameTeam),
                                pokemon5.findViewById<ImageView>(R.id.pokemonImageTeam),
                                userTeam["Pokemon4"]!!)
                        if(userTeam["Pokemon5"] !=null)
                            addPokemonToUi(pokemon6.findViewById<EditText>(R.id.pokemonNameTeam),
                                pokemon6.findViewById<ImageView>(R.id.pokemonImageTeam),
                                userTeam["Pokemon5"]!!)
                    }

                }
                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Log.w("Error", "Failed to read value.", error.toException())
                }
            })

            }


        setOnClickForCards(pokemonList)

    }

    //Set on clickForCards, adiciona onLickListener para todos os cards
    //No on click se o texto estiver preenchido é procurado o pokémon, se existir, o nome e imagem são adicionados
    fun setOnClickForCards(cards : List<MaterialCardView>){
        for((aux, card) in cards.withIndex()){
            val name = card.findViewById<EditText>(R.id.pokemonNameTeam)
            val image = card.findViewById<ImageView>(R.id.pokemonImageTeam)
            card.setOnClickListener{
                if(!lock){

                    if(name.text.toString().isNotEmpty()){
                        addPokemonToUi(name, image ,name.text.toString())
                        val addPokemonSound = MediaPlayer.create(this,R.raw.mon_add)
                        addPokemonSound.start()


                    }

                    else
                        Toast.makeText(applicationContext, "Fill the Name Field please", Toast.LENGTH_SHORT).show()
                }
                else{
                    if(name.text.toString().isNotEmpty()){
                        val intent = Intent(this, TeamBuilderSelection::class.java)
                        intent.putExtra("pokemonName", name.text.toString())
                        intent.putExtra("pokemonSlot", "Pokemon$aux")
                        startActivity(intent)
                    }
                }


            }
        }
    }


    //Salvar a informação dos pokémon que se encontram seleccionados para uma lista
    fun saveCardInfo(cards : List<MaterialCardView>){
        for ((aux, card) in cards.withIndex()){
            val name = card.findViewById<EditText>(R.id.pokemonNameTeam)
            if(name.text.toString().isNotEmpty()){
                team["Pokemon$aux"] = name.text.toString().lowercase()
            }
        }
    }

    //Método que faz o query para obter o pokémon e associar a sua informação com o cartão
    //correspondente recebido em argumento
    fun addPokemonToUi(text: TextView, image: ImageView, pokemonName: String) {
        val pokemonApi = PokemonApi.create().getPokemon(pokemonName)

        pokemonApi.enqueue(object : Callback<Pokemon> {
            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                Log.d("Pokemon", "Success")
                if(response.body() != null){

                    var name: String = response.body()!!.name
                    var url : String = response.body()!!.getImageUrl()
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

    //Sair da actividade
    override fun onSupportNavigateUp(): Boolean {
        val saveSound = MediaPlayer.create(this,R.raw.save)
        saveSound.start()
        if(FirebaseAuth.getInstance().currentUser!=null)
        {
            //Sallvar a informação toda na lista pokemonList, enviar esta lista para a Database na àrvore associada ao User
            saveCardInfo(pokemonList)
            FirebaseDatabase.getInstance("https://trainer-companion-app-default-rtdb.europe-west1.firebasedatabase.app").reference.child(FirebaseAuth.getInstance().currentUser!!.uid)
                .child("Team").updateChildren(team as Map<String, Any>)
        }

        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        val selection = MediaPlayer.create(this,R.raw.basic_press)
        if (id == R.id.lock_team) {
                selection.start()
                lock = !lock
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.team_builder_menu, menu)
        return true
    }


}