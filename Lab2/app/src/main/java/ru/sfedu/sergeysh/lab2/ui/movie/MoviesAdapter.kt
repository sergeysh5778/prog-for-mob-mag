package ru.sfedu.sergeysh.lab2.ui.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.sfedu.sergeysh.lab2.data.movie.MovieCard
import ru.sfedu.sergeysh.lab2.databinding.ListItemMovieBinding

/**
 * Adapter for the [RecyclerView] in [MoviesFragment].
 */

class MoviesAdapter(
    private var dataSet: List<MovieCard>, private val clickCallback: (String) -> Unit
) : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        return MoviesViewHolder(
            ListItemMovieBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), clickCallback
        )
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount() = dataSet.size

    fun updateMovieList(dataSet: List<MovieCard>) {
        this.dataSet = dataSet
        notifyDataSetChanged()
    }

    class MoviesViewHolder(
        private val binding: ListItemMovieBinding, clickCallback: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                clickCallback(movieUrl)
            }
        }

        private lateinit var movieUrl: String

        fun bind(movieCard: MovieCard) {
            movieUrl = movieCard.movieUrl

            Glide.with(binding.root).load(movieCard.posterUrl).into(binding.posterImageView)

            binding.nameTextView.text = movieCard.name
            binding.genreTextView.text = movieCard.genre
            binding.yearTextView.text = movieCard.year
        }
    }
}
