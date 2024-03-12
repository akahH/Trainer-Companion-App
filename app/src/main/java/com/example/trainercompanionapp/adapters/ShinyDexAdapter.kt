package com.example.trainercompanionapp.adapters

import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.trainercompanionapp.apiObjects.Pokemon
import com.example.trainercompanionapp.R.*
import com.google.android.material.card.MaterialCardView

class ShinyDexAdapter(
    private val pokemonList: List<Pokemon>,
    private val offset: Int,
    private val caughtShiny: MutableMap<String, String>
) : RecyclerView.Adapter<ShinyDexAdapter.ViewHolder>(), Filterable  {

    private var pokemonListFilter : MutableList<Pokemon> = mutableListOf()
    init {
        pokemonListFilter.addAll(pokemonList)
    }



    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val textView : TextView
        val imageView : ImageView
        val card : MaterialCardView
        init {
            textView = view.findViewById(id.pokemonNameTeam)
            imageView = view.findViewById(id.pokemonImageTeam)
            card = view.findViewById(id.pokemonCard)
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(layout.team_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.textView.text = pokemonListFilter[position].name.uppercase()
            holder.textView.isEnabled = false
            if(caughtShiny[pokemonListFilter[position].name] == "Caught")
                holder.card.setCardBackgroundColor(ContextCompat.getColor(holder.card.context, color.whiteApp))
            else
                holder.card.setCardBackgroundColor(ContextCompat.getColor(holder.card.context, color.notSelected))
            val index = pokemonListFilter[position].url.dropLast(1).substringAfterLast("/")


            val selection = MediaPlayer.create(holder.card.context,raw.basic_press)
            Glide.with(holder.imageView.context)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/$index.png")
                .into(holder.imageView)
            holder.card.setOnClickListener{
                if(caughtShiny[pokemonListFilter[position].name] == "Caught"){
                    selection.start()
                    holder.card.setCardBackgroundColor(ContextCompat.getColor(holder.card.context, color.notSelected))
                    caughtShiny[pokemonListFilter[position].name] = "Not Caught"
                    holder.card.strokeWidth = 0
                }
                else{
                    selection.start()
                    holder.card.setCardBackgroundColor(ContextCompat.getColor(holder.card.context, color.whiteApp))
                    holder.card.strokeColor = ContextCompat.getColor(holder.card.context, color.grass)
                    holder.card.strokeWidth = 15
                    caughtShiny[pokemonListFilter[position].name] = "Caught"
                }


        }

    }

    override fun getItemCount(): Int {

        return pokemonListFilter.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList : MutableList<Pokemon> = mutableListOf()
                if(constraint.isNullOrEmpty()){
                    filteredList.addAll(pokemonList)
                }
                else{
                    for(pokemon in pokemonList)
                        if(pokemon.name.lowercase().contains(constraint.toString().lowercase())){
                            filteredList.add(pokemon)
                        }
                }
                return FilterResults().apply { values = filteredList }

            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                pokemonListFilter.clear()
                if (results != null) {
                    pokemonListFilter.addAll(results.values as List<Pokemon>)
                }
                notifyDataSetChanged()

            }
        }
    }


}