package com.example.trainercompanionapp.ui

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trainercompanionapp.R
import com.example.trainercompanionapp.adapters.DexFilterAdapter

class SelectDexActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_dex)

        val recyclerView = findViewById<RecyclerView>(R.id.dexFilterList)
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar!!.title ="National Dex"


        val imageList : List<Int> =
            listOf(
                R.drawable.first_gen,
                R.drawable.second_gen, R.drawable.third_gen,
                R.drawable.forth_gen, R.drawable.fifth_gen,
                R.drawable.sixth_gen, R.drawable.seventh_gen,
                R.drawable.eight_gen
            )

        val adapter = DexFilterAdapter(imageList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(this, 1)

    }



    override fun onSupportNavigateUp(): Boolean {
        val exit = MediaPlayer.create(this,R.raw.exit_outside)
        exit.start()
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}



