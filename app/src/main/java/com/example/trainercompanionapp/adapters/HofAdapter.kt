package com.example.trainercompanionapp.adapters

import android.content.Intent
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.trainercompanionapp.ui.HallOfFameTeam
import com.example.trainercompanionapp.R
import com.google.android.material.card.MaterialCardView

class HofAdapter(private val teams : Map<String, Map<String, Map<String, String>>>) : RecyclerView.Adapter<HofAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val gamName : TextView
        val pokemon1 : ImageView
        val pokemon2 : ImageView
        val pokemon3 : ImageView
        val pokemon4 : ImageView
        val pokemon5 : ImageView
        val pokemon6 : ImageView
        val card : MaterialCardView
        init {
            gamName = view.findViewById(R.id.gameName)
            pokemon1 = view.findViewById(R.id.hofTeam1)
            pokemon2 = view.findViewById(R.id.hofTeam2)
            pokemon3 = view.findViewById(R.id.hofTeam3)
            pokemon4 = view.findViewById(R.id.hofTeam4)
            pokemon5 = view.findViewById(R.id.hofTeam5)
            pokemon6 = view.findViewById(R.id.hofTeam6)
            card = view.findViewById(R.id.hofItem)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.hof_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val entry = teams.keys.toTypedArray()[position]
        var id = teams[entry]?.get("Pokemon0")?.get("Id").toString()
        holder.gamName.text = entry


        //Verifica se o pokémon existe para colocar o seu sprite, se não existir coloca uma imagem default
        Glide.with(holder.pokemon1.context)
            .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png")
            .into(holder.pokemon1);
        if(teams[entry]?.get("Pokemon0") == null)
            Glide.with(holder.pokemon2.context)
                .load(R.drawable.pokeball_cover)
                .into(holder.pokemon1);

        id = teams[entry]?.get("Pokemon1")?.get("Id").toString()
        Glide.with(holder.pokemon2.context)
            .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png")
            .into(holder.pokemon2);
        if(teams[entry]?.get("Pokemon1") == null)
            Glide.with(holder.pokemon2.context)
                .load(R.drawable.pokeball_cover)
                .into(holder.pokemon2);


        id = teams[entry]?.get("Pokemon2")?.get("Id").toString()
        Glide.with(holder.pokemon3.context)
            .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png")
            .into(holder.pokemon3);
        if(teams[entry]?.get("Pokemon2") == null)
            Glide.with(holder.pokemon3.context)
                .load(R.drawable.pokeball_cover)
                .into(holder.pokemon3);


        id = teams[entry]?.get("Pokemon3")?.get("Id").toString()
        Glide.with(holder.pokemon4.context)
            .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png")
            .into(holder.pokemon4);
        if(teams[entry]?.get("Pokemon3") == null)
            Glide.with(holder.pokemon4.context)
                .load(R.drawable.pokeball_cover)
                .into(holder.pokemon4);

        id = teams[entry]?.get("Pokemon4")?.get("Id").toString()
        Glide.with(holder.pokemon5.context)
            .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png")
            .into(holder.pokemon5);
        if(teams[entry]?.get("Pokemon4") == null)
            Glide.with(holder.pokemon5.context)
                .load(R.drawable.pokeball_cover)
                .into(holder.pokemon5);


        id = teams[entry]?.get("Pokemon5")?.get("Id").toString()
        Glide.with(holder.pokemon6.context)
            .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png")
            .into(holder.pokemon6);
        if(teams[entry]?.get("Pokemon5") == null)
            Glide.with(holder.pokemon6.context)
                .load(R.drawable.pokeball_cover)
                .into(holder.pokemon6);

        //Guarda os pokémon todos que estejam na equipa, guarda num array e envia para a nova actividade
        holder.card.setOnClickListener{
            val pokemonList : ArrayList<String> = arrayListOf(teams[entry]?.get("Pokemon0")?.get("Id").toString(),
                teams[entry]?.get("Pokemon1")?.get("Id").toString(),
                teams[entry]?.get("Pokemon2")?.get("Id").toString(),
                teams[entry]?.get("Pokemon3")?.get("Id").toString(),
                teams[entry]?.get("Pokemon4")?.get("Id").toString(),
                teams[entry]?.get("Pokemon5")?.get("Id").toString())
                val game = entry.substringAfter(" ")
                val intent = Intent(holder.card.context, HallOfFameTeam::class.java)
                val selection = MediaPlayer.create(holder.card.context,R.raw.basic_press)
                selection.start()
                intent.putStringArrayListExtra("team", pokemonList)
                intent.putExtra("gameImage", getGameImage(game))
                intent.putExtra("gameName", game)
                holder.card.context.startActivity(intent)
        }
    }

    fun getGameImage(game : String): Int {
        when(game){
            "Yellow"->
                return R.drawable.yellow
            "Blue"->
                return R.drawable.blue
            "Red"->
                return R.drawable.red
            "Silver"->
                return R.drawable.silver
            "Gold"->
                return R.drawable.gold
            "Crystal"->
                return R.drawable.crystal
            "Ruby"->
                return R.drawable.ruby
            "Sapphire"->
                return R.drawable.sapphire
            "Emerald"->
                return R.drawable.emerald
            "Pearl"->
                return R.drawable.pearl
            "Diamond"->
                return R.drawable.diamond
            "Platinum"->
                return R.drawable.platinum
            "Black"->
                return R.drawable.black
            "White"->
                return R.drawable.white
            "White 2"->
                return R.drawable.white_two
            "Black 2"->
                return R.drawable.black_two
            "Sun"->
                return R.drawable.sun
            "Moon"->
                return R.drawable.moon
            "Ultra Sun"->
                return R.drawable.ultra_sun
            "Ultra Moon"->
                return R.drawable.ultra_moon
            "Sword"->
                return R.drawable.sword
            "Shield"->
                return R.drawable.shield
        }
        return 0
    }

    override fun getItemCount(): Int {
        return teams.size
    }
}

