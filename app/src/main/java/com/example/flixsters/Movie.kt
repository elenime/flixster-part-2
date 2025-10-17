package com.example.flixsters

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Movie (
    val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("vote_average") val voteAverage: Float,
    @SerializedName("vote_count") val voteCount: Int,
    @SerializedName("original_language") val originalLanguage: String?
) : Serializable {
    fun posterUrl() = posterPath?.let { "https://image.tmdb.org/t/p/w500/$it" }
    fun backdropUrl() = backdropPath?.let { "https://image.tmdb.org/t/p/w500/$it" }
}