package com.example.flixsters

import android.graphics.Movie

data class MovieResponse (
    val page: Int,
    val results: List<com.example.flixsters.Movie>
)