package ru.sfedu.sergeysh.lab3.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.sfedu.sergeysh.common.data.movie.MovieItem
import ru.sfedu.sergeysh.common.data.movie.MovieRepository

class MovieViewModel : ViewModel() {

    private val movieRepository: MovieRepository = MovieRepository()

    private val _movieList = MutableLiveData<List<MovieItem>>()
    val movieList: LiveData<List<MovieItem>> = _movieList

    init {
        if (_movieList.value?.isEmpty() != false) {
            viewModelScope.launch {
                _movieList.value = movieRepository.getSimpleMovieList()
            }
        }
    }
}
