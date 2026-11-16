package com.moniliu.buscadorpeliculas.data

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("Title") val title: String,
    @SerializedName("Year") val year: String,
    @SerializedName("imdbID") val imdbId: String,
    @SerializedName("Type") val type: String,
    @SerializedName("Poster") val poster: String,


)

data class MovieSearchResponse(
    @SerializedName("Search") val search: List<Movie>?,
    @SerializedName("totalResults") val totalResults: String,
    @SerializedName("Response") val response: String,

)

data class MovieDetail(
    @SerializedName("Title") val title: String,
    @SerializedName("Year") val year: String,
    @SerializedName("Poster") val poster: String,
    @SerializedName("Plot") val synopsis: String,
    @SerializedName("Runtime") val duration: String,
    @SerializedName("Director") val director: String,
    @SerializedName("Genre") val genre: String,
    @SerializedName("Country") val country: String,
    )