package com.example.geekbrainsmoviesapp.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.geekbrainsmoviesapp.model.AppState
import com.example.geekbrainsmoviesapp.model.Movie
import com.example.geekbrainsmoviesapp.model.MoviesFilter
import com.example.geekbrainsmoviesapp.repository.MoviesRepository
import com.example.geekbrainsmoviesapp.repository.MoviesRepositoryImpl

class MoviesListViewModel : ViewModel() {
    private val moviesRepository: MoviesRepository = MoviesRepositoryImpl()
    private val liveDataAppState: MutableLiveData<AppState<List<Movie>>> = MutableLiveData()
    private val liveDataCurrentMovie: MutableLiveData<Movie> = MutableLiveData()

    fun getLiveDataAppState() = liveDataAppState

    fun getLiveDataCurrentMovie() = liveDataCurrentMovie

    fun getMovies(moviesFilter: MoviesFilter) {
        when (moviesFilter) {
            MoviesFilter.All -> {
                liveDataAppState.value = AppState.Loading()
                val list = moviesRepository.getAllMovies()
                liveDataAppState.value = AppState.Success(list)
            }
            MoviesFilter.Favorites -> {
                liveDataAppState.value = AppState.Loading()
                val list = moviesRepository.getFavoritesMovies()
                liveDataAppState.value = AppState.Success(list)
            }
            MoviesFilter.Rating -> {
                liveDataAppState.value = AppState.Loading()
                val list = moviesRepository.getRatingsMovies()
                liveDataAppState.value = AppState.Success(list)
            }
        }
    }

    fun setMovieOpen(movie: Movie) {
        liveDataCurrentMovie.value = movie
    }

}
