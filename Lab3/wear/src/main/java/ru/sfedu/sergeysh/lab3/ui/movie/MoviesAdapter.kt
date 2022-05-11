package ru.sfedu.sergeysh.lab3.ui.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.sfedu.sergeysh.common.data.movie.MovieItem
import ru.sfedu.sergeysh.lab3.databinding.ListItemMovieBinding

/**
 * Adapter for the [RecyclerView] in [MoviesFragment].
 */

class MoviesAdapter(private var dataSet: List<MovieItem>) :
    RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            ListItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount() = dataSet.size

    fun updateMovieList(dataSet: List<MovieItem>) {
        this.dataSet = dataSet
        notifyItemRangeChanged(0, dataSet.size)
    }

    class MovieViewHolder(private val binding: ListItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movieItem: MovieItem) {
            binding.nameTextView.text = movieItem.name
        }
    }
}
