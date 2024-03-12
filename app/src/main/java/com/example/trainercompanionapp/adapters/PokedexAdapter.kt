package com.example.trainercompanionapp.adapters

import android.content.Intent
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.trainercompanionapp.apiObjects.Pokemon
import com.example.trainercompanionapp.ui.PokedexSelectionActivity
import com.example.trainercompanionapp.R

class PokedexAdapter( private  val pokemonList : List<Pokemon>, private val offset : Int) :
    RecyclerView.Adapter<PokedexAdapter.ViewHolder>(), Filterable {

    private var pokemonListFilter: MutableList<Pokemon> = mutableListOf()

    init {
        pokemonListFilter.addAll(pokemonList)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView
        val imageView: ImageView

        init {
            textView = view.findViewById(R.id.pokemonNameTeam)
            imageView = view.findViewById(R.id.pokemonImageTeam)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.team_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = pokemonListFilter[position].name.uppercase()
        holder.textView.isEnabled = false
        val index = pokemonListFilter[position].url.dropLast(1).substringAfterLast("/")
        Glide.with(holder.imageView.context)
            .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$index.png")
            .into(holder.imageView);
        holder.imageView.setOnClickListener {
            val intent = Intent(holder.imageView.context, PokedexSelectionActivity::class.java)
            val enterPokemon = MediaPlayer.create(holder.imageView.context,R.raw.dex_selection)
            enterPokemon.start()
            intent.putExtra("id", index)
            holder.imageView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return pokemonListFilter.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                var filteredList: MutableList<Pokemon> = mutableListOf()
                if (constraint.isNullOrEmpty()) {
                    filteredList.addAll(pokemonList)
                } else {
                    for (pokemon in pokemonList)
                        if (pokemon.name.lowercase()
                                .contains(constraint.toString().toLowerCase())
                        ) {
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