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

    fun getLiveData() = liveDataAppState

    fun getMovies(moviesFilter: MoviesFilter) {
        val movie = Movie(0, "ru", "Title", "Title", "Description", "", "", 0)
        val list: List<AppState<List<Movie>>> = mutableListOf(
            AppState.Success(
                mutableListOf(
                    movie.copy(id = 1),
                    movie.copy(id = 2),
                    movie.copy(id = 3),
                    movie.copy(id = 4),
                    movie.copy(id = 5),
                    movie.copy(id = 6),
                    movie.copy(id = 7),
                ),
            ),
            AppState.Error(Exception("New Error")),
            AppState.Loading(),
        )

        liveDataAppState.value = list.shuffled().first()
    }

}
