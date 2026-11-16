package com.moniliu.buscadorpeliculas.adapters

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import com.moniliu.buscadorpeliculas.data.Movie
import com.moniliu.buscadorpeliculas.databinding.ItemMovieBinding
import com.squareup.picasso.Picasso

class MovieAdapter (
    var items : List<Movie>,
    val onItemClick: (position: Int)-> Unit
): RecyclerView.Adapter<MovieViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieBinding.inflate(

            layoutInflater, parent, false
        )
        return MovieViewHolder(binding)

    }

    override fun onBindViewHolder(
        holder: MovieViewHolder,
        position: Int){

        val movie = items [position]
        holder.render(movie)

        holder.binding.movieCard.setOnClickListener{
           val currentPosition = holder.bindingAdapterPosition

            if (currentPosition != RecyclerView.NO_POSITION){
                onItemClick(currentPosition)
            }
        }


}

override fun getItemCount(): Int = items.size

fun updateData (dataSet: List<Movie>){
    items = dataSet
    notifyDataSetChanged()
}
}
class MovieViewHolder (
    val binding : ItemMovieBinding
): RecyclerView.ViewHolder(binding.root){

    fun render (movie: Movie){
        binding.titleTextView.text = movie.title
        binding.yearTextView.text = movie.year

        binding.genreChip.visibility = View.GONE

        Picasso.get()
            .load(movie.poster.takeIf { it !="N/A"})
            .into(binding.posterImageView)
    }


}

