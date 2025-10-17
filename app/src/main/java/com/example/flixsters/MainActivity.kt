package com.example.flixsters

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {

    private lateinit var rv: RecyclerView
    private lateinit var progress: ProgressBar
    private val adapter = MoviesAdapter(onClick = { movie ->
        val i = Intent(this, DetailsActivity::class.java)
        i.putExtra("movie", movie)
        startActivity(i)
    })

    private val API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        rv = findViewById(R.id.rvMovies)
        progress = ProgressBar(this).apply { visibility = View.GONE }

        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        fetchNowPlaying()
    }

    private fun fetchNowPlaying() {
        progress.isVisible = true

        lifecycleScope.launch {
            try {
                val resp = RetrofitInstance.api.getNowPlaying(API_KEY)

                if (resp.isSuccessful) {
                    val movies = resp.body()?.results.orEmpty()
                    adapter.submit(movies)
                    if (movies.isEmpty()) {
                        Toast.makeText(this@MainActivity, "No movies found", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val msg = withContext(Dispatchers.IO) {
                        resp.errorBody()?.string()?.take(120)
                    }
                    Toast.makeText(
                        this@MainActivity,
                        "HTTP ${resp.code()} ${resp.message()}${if (!msg.isNullOrBlank()) "\n$msg" else ""}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@MainActivity,
                    "Network error: ${e.localizedMessage ?: e.javaClass.simpleName}",
                    Toast.LENGTH_LONG
                ).show()
            } finally {
                progress.isVisible = false
            }
        }
    }
}