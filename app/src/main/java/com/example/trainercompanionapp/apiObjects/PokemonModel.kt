package com.example.trainercompanionapp.apiObjects


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class PokemonList(

    @field:SerializedName("results")
    val results: List<Pokemon>
)



@Entity
data class Pokemon (
    @PrimaryKey
    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id : Int,

    @field:SerializedName("url")
    val url : String,

    @field:SerializedName("types")
    val types: List<TypeRequest>,

    @field:SerializedName("stats")
    val stats: List<Stats>,

    @field:SerializedName("species")
    val species: PokemonSpecies,

    @field:SerializedName("sprites")
    val sprites: Sprites

    ) {
    fun getImageUrl(): String {
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${id.toString()}.png"
    }

    fun getOfficialArtwork():String{
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${id.toString()}.png"
    }

    fun getID(): String = id.toString()
}

data class Sprites (
    @field:SerializedName("front_shiny")
    val url :String
        )

data class TypeRequest(
        @field:SerializedName("slot")
        val slot: Int,

        @field:SerializedName("type")
        val type: Type
    )
    @Entity
    data class Type(
        @PrimaryKey
        @field:SerializedName("name")
        val name: String,

        @field:SerializedName("url")
        val url: String
    )

@Entity
data class Stats(
    @field:SerializedName("base_stat")
    val base_stat: Int,
    @PrimaryKey
    @field:SerializedName("stat")
    val stat: Stat,
)

    data class Stat(
    @field:SerializedName("name")
        val name: String
    )

@Entity
data class PokemonSpecies(
    @PrimaryKey
    @field:SerializedName("url")
    val url: String
)

@Entity
data class Species (
    @PrimaryKey
    @field:SerializedName("name")
    val name : String,

    @field:SerializedName("evolution_chain")
    val evolution_chain:EvolutionChain
        )

@Entity
data class EvolutionChain(
    @PrimaryKey
    @field:SerializedName("url")
    val url : String
)

@Entity
data class Evolution(
    @PrimaryKey
    @field:SerializedName("id")
    val id : String,

    @field:SerializedName("chain")
    val chain : Chain
)

@Entity
data class Chain(
    @PrimaryKey
    @field:SerializedName("species")
    val species: Species,

    @field:SerializedName("evolves_to")
    val evolvesTo: List<EvolvesTo>
)

@Entity
data class EvolvesTo(
    @PrimaryKey
    @field:SerializedName("species")
    val species : Species,

    @field:SerializedName("evolves_to")
    val evolvesTo: List<EvolvesTo>
)
