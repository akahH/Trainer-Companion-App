package com.example.trainercompanionapp.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.trainercompanionapp.apiObjects.TypeRequest
import com.example.trainercompanionapp.R
import com.google.android.material.card.MaterialCardView

class TypeAdapter(private val typeList: List<TypeRequest>) : RecyclerView.Adapter<TypeAdapter.ViewHolder>() {


    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val textView : TextView
        val cardView : MaterialCardView
        init {
            textView = view.findViewById(R.id.typeName)
            cardView = view.findViewById(R.id.typeCard)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.type_template, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = typeList[position].type.name.uppercase()
        val color = typeColor(typeList[position].type.name)
        if (color != null) {
            Log.d("type", color.toString())
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(holder.cardView.context, color))
        }
    }

    override fun getItemCount(): Int {
        return typeList.size
    }

    private fun typeColor(typeName : String) : Int? {
        Log.d("type", typeName)
        when(typeName){
            "fire"->{
                Log.d("type", "enter")
                return R.color.fire
            }
            "normal"->{
                return R.color.normal
            }
            "water"->{
                return R.color.water
            }
            "grass"->{
                return R.color.grass
            }
            "electric"->{
                return R.color.electric
            }
            "ice"->{
                return R.color.ice
            }
            "fighting"->{
                return R.color.fighting
            }
            "poison"->{
                return R.color.poison
            }
            "ground"->{
                return R.color.ground
            }
            "flying"->{
                return R.color.flying
            }
            "psychic"->{
                return R.color.psychic
            }
            "bug"->{
                return R.color.bug
            }
            "dark"->{
                return R.color.dark
            }
            "rock"->{
                return R.color.rock
            }
            "ghost"->{
                return R.color.ghost
            }
            "dragon"->{
                return R.color.dragon
            }
            "steel"->{
                return R.color.steel
            }
            "fairy"->{
                return R.color.fairy
            }
        }
    return null
    }
}