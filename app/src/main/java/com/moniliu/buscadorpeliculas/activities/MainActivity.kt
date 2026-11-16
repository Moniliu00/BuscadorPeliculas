package com.moniliu.buscadorpeliculas.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moniliu.buscadorpeliculas.R
import com.moniliu.buscadorpeliculas.adapters.MovieAdapter
import com.moniliu.buscadorpeliculas.data.Movie
import com.moniliu.buscadorpeliculas.data.MovieApi
import com.moniliu.buscadorpeliculas.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class  MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    lateinit var adapter: MovieAdapter
    var movieList: List<Movie> = emptyList()

    var filteredMovieList: List<Movie> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(
                android.graphics.Color.rgb(17, 18, 34)
            ),
            navigationBarStyle = SystemBarStyle.dark(
                android.graphics.Color.rgb(17, 18, 34)
            )
        )

        // Configuramos el View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )
            insets
        }

        // Configuramos el RecyclerView
        adapter = MovieAdapter(emptyList()) { position ->
            val movie = filteredMovieList[position]

            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_MOVIE_ID, movie.imdbId)
            startActivity(intent)
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            try {
                val api = MovieApi.getInstance()

                val result = api.getMoviesList(
                    apiKey = "fb7aca4",
                    title = "Batman"
                )

                movieList = result.search ?: emptyList()
                filteredMovieList = movieList
                showData()

            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(
                    this@MainActivity,
                    "Error al cargar películas",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_main_menu, menu)

        val searchMenuItem = menu.findItem(R.id.menu_search)
        val searchView = searchMenuItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filteredMovieList = movieList.filter {
                    it.title.contains(newText, true)
                }

                showData()
                return true
            }
        })

        return true
    }

    fun showData() {
        adapter.updateData(filteredMovieList)
        supportActionBar?.subtitle = "Found ${filteredMovieList.size} games"
    }
}
