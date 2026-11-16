package com.moniliu.buscadorpeliculas.activities

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.moniliu.buscadorpeliculas.data.MovieApi
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch


    class DetailActivity : AppCompatActivity() {

        companion object {
            const val EXTRA_MOVIE_ID = "MOVIE_ID"
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            val layout = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(32, 32, 32, 32)
            }

            val poster = ImageView(this).apply {
                adjustViewBounds = true
            }

            val title = TextView(this).apply {
                textSize = 24f
            }

            val info = TextView(this).apply {
                textSize = 16f
                setPadding(0, 16, 0, 0)
            }

            layout.addView(poster)
            layout.addView(title)
            layout.addView(info)

            val scrollView = ScrollView(this)
            scrollView.addView(layout)
            setContentView(scrollView)

            val movieId = intent.getStringExtra(EXTRA_MOVIE_ID)

            if (movieId.isNullOrBlank()) {
                Toast.makeText(
                    this,
                    "No se encontró la película",
                    Toast.LENGTH_LONG
                ).show()
                finish()
                return
            }

            lifecycleScope.launch {
                try {
                    val movie = MovieApi.getInstance().getMovieById(
                        apiKey = "fb7aca4",
                        id = movieId
                    )

                    title.text = movie.title

                    info.text = """
                    Año: ${movie.year}
                    Género: ${movie.genre}
                    Director: ${movie.director}
                    Duración: ${movie.duration}
                    País: ${movie.country}

                    Sinopsis:
                    ${movie.synopsis}
                """.trimIndent()

                    Picasso.get()
                        .load(movie.poster.takeIf { it != "N/A" })
                        .into(poster)

                } catch (e: Exception) {
                    Toast.makeText(
                        this@DetailActivity,
                        "Error al cargar el detalle",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        override fun onSupportNavigateUp(): Boolean {
            finish()
            return true
        }
    }