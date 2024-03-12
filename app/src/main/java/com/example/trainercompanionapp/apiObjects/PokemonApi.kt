package com.example.trainercompanionapp.apiObjects

import androidx.viewbinding.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {

    @GET("pokemon/{pokemonId}")
    fun getPokemon(
        @Path("pokemonId") pokemonId : String
    ) : Call<Pokemon>

    @GET("pokemon-species/{pokemonName}")
    fun getSpecies(
        @Path("pokemonName") pokemonName : String
    ) : Call<Species>

    @GET("evolution-chain/{pokemonName}")
    fun getEvolutions(
        @Path("pokemonName") pokemonName : String
    ) : Call<Evolution>

    @GET("pokemon")
    fun getListPokemon(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Call<PokemonList>

    companion object{
        var BASE_URL = "https://pokeapi.co/api/v2/"

        fun create() : PokemonApi {
            val loggingInterceptor = if (BuildConfig.DEBUG){
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
            }
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(PokemonApi::class.java)
        }
    }
}