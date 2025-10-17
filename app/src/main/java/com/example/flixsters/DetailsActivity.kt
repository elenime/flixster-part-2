package com.example.flixsters

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val movie = intent.getSerializableExtra("movie") as Movie

        val ivBackdrop: ImageView = findViewById(R.id.ivBackdrop)
        val tvTitle: TextView = findViewById(R.id.tvDetailTitle)
        val tvRelease: TextView = findViewById(R.id.tvRelease)
        val tvRating: TextView = findViewById(R.id.tvRating)
        val tvVotes: TextView = findViewById(R.id.tvVotes)
        val tvLang: TextView = findViewById(R.id.tvLanguage)
        val tvOverview: TextView = findViewById(R.id.tvDetailOverview)

        tvTitle.text = movie.title
        tvOverview.text = movie.overview
        tvRelease.text = "Release date: ${movie.releaseDate ?: "—"}"
        tvRating.text  = "Rating: ${movie.voteAverage}"
        tvVotes.text   = "Votes: ${movie.voteCount}"
        tvLang.text    = "Language: ${movie.originalLanguage ?: "—"}"

        Glide.with(this).load(movie.backdropUrl()).into(ivBackdrop)
    }
}