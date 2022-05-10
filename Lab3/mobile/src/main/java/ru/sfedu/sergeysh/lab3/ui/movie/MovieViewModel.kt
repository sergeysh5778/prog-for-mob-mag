package ru.sfedu.sergeysh.lab3.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.sfedu.sergeysh.lab3.data.movie.MovieCard
import ru.sfedu.sergeysh.lab3.data.movie.MovieDetails
import ru.sfedu.sergeysh.lab3.data.movie.MovieRepository

class MovieViewModel : ViewModel() {

    private val movieRepository: MovieRepository = MovieRepository()

    private val _movieList = MutableLiveData<List<MovieCard>>()
    val movieList: LiveData<List<MovieCard>> = _movieList

    private val _selectedMovie = MutableLiveData<MovieDetails?>()
    val selectedMovie: LiveData<MovieDetails?> = _selectedMovie

    init {
        if (_movieList.value?.isEmpty() != false) {
            viewModelScope.launch {
                _movieList.value = movieRepository.getMovieList()
            }
        }
    }

    fun setSelectedMovie(movieUrl: String) {
        if (movieUrl != _selectedMovie.value?.movieUrl) {
            viewModelScope.launch {
                _selectedMovie.value = null
                _selectedMovie.value = movieRepository.getMovieDetails(movieUrl)
            }
        }
    }
}
