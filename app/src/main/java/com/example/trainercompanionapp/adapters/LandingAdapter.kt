package com.example.trainercompanionapp.adapters

import android.app.Activity
import android.content.Intent
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.trainercompanionapp.R
import com.example.trainercompanionapp.ui.MainActivity
import com.example.trainercompanionapp.ui.PokedexSelectionActivity

class LandingAdapter(private val pokemonList : List<Int> ) : RecyclerView.Adapter<LandingAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imageView: ImageView

        init {
            imageView = view.findViewById(R.id.pokemonSprite)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.sprite_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(holder.imageView.context)
            .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${pokemonList[position]}.png")
            .into(holder.imageView);
        holder.imageView.setOnClickListener {
            val intent = Intent(holder.imageView.context, PokedexSelectionActivity::class.java)
            val enterPokemon = MediaPlayer.create(holder.imageView.context,R.raw.dex_selection)
            enterPokemon.start()
            intent.putExtra("id", pokemonList[position].toString())
            intent.putExtra("toLanding", "true")
            holder.imageView.context.startActivity(intent)
            val activity : MainActivity = holder.imageView.context as MainActivity
            activity.finish()
        }
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }
}