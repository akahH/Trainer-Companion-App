package com.example.trainercompanionapp.adapters

import android.content.Intent
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.trainercompanionapp.R
import com.example.trainercompanionapp.apiObjects.Pokemon
import com.example.trainercompanionapp.ui.PokedexSelectionActivity

class SpriteAdapter(private val spriteList : List<String>) : RecyclerView.Adapter<SpriteAdapter.ViewHolder>() {



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
            .load(spriteList[position])
            .into(holder.imageView);

    }

    override fun getItemCount(): Int {
        return spriteList.size
    }
}