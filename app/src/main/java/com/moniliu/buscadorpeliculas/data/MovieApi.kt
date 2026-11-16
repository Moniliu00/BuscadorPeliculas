package com.moniliu.buscadorpeliculas.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET(".")
    suspend fun getMoviesList(
        @Query("apiKey") apiKey: String,
        @Query("s")title: String
    ): MovieSearchResponse

    @GET(".")
    suspend fun getMovieById(
        @Query("apiKey") apiKey: String,
        @Query("i") id: String
    ): MovieDetail

    companion object {
            fun getInstance(): MovieApi
    {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.omdbapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(MovieApi::class.java)
    }

    }
    }



