package com.example.trainercompanionapp.adapters

import android.content.Intent
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.trainercompanionapp.ui.PokedexActivity
import com.example.trainercompanionapp.R


class DexFilterAdapter( private  val images : List<Int>) : RecyclerView.Adapter<DexFilterAdapter.ViewHolder>() {

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val imageView : ImageView
        init {
            imageView = view.findViewById(R.id.genImage)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.select_dex_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imageView.setImageResource(images[position])
        holder.imageView.setOnClickListener {
            val selection = MediaPlayer.create(holder.imageView.context,R.raw.basic_press)
            selection.start()
            val intent = Intent(holder.imageView.context, PokedexActivity::class.java)
            intent.putExtra("gen", position+1)
            holder.imageView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return images.size
    }


}