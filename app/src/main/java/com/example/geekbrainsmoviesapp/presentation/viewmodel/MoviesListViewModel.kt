package com.example.geekbrainsmoviesapp.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.geekbrainsmoviesapp.model.*
import com.example.geekbrainsmoviesapp.repository.MoviesRepository
import com.example.geekbrainsmoviesapp.repository.MoviesRepositoryImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class MoviesListViewModel : ViewModel() {
    private val moviesRepository: MoviesRepository = MoviesRepositoryImpl()
    private val liveDataAppState: MutableLiveData<AppState<List<Movie>>> = MutableLiveData()
    private val liveDataCurrentMovie: MutableLiveData<AppState<MovieDetails>> = MutableLiveData()
    private val liveDataCurrentIdMovie: MutableLiveData<Int> = MutableLiveData()

    fun getLiveDataAppState() = liveDataAppState

    fun getLiveDataCurrentMovie() = liveDataCurrentMovie

    fun getLiveDataCurrentIdMovie() = liveDataCurrentIdMovie

    fun getMovies(moviesFilter: MoviesFilter) {
        when (moviesFilter) {
            MoviesFilter.All -> {
                liveDataAppState.value = AppState.Loading()
                moviesRepository.getAllMovies().enqueue(object: Callback<ResultListApi> {
                    override fun onResponse(
                        call: Call<ResultListApi>,
                        response: Response<ResultListApi>
                    ) {
                        liveDataAppState.value = AppState.Success(response.body()?.results ?: mutableListOf())
                    }

                    override fun onFailure(call: Call<ResultListApi>, t: Throwable) {
                        liveDataAppState.value = AppState.Error(Exception(t))
                    }

                })
            }
            MoviesFilter.Favorites -> {
                liveDataAppState.value = AppState.Loading()
                val list = moviesRepository.getFavoritesMovies()
                liveDataAppState.value = AppState.Success(list)
            }
            MoviesFilter.Rating -> {
                liveDataAppState.value = AppState.Loading()
                moviesRepository.getRatingsMovies().enqueue(object: Callback<ResultListApi> {
                    override fun onResponse(
                        call: Call<ResultListApi>,
                        response: Response<ResultListApi>
                    ) {
                        liveDataAppState.value = AppState.Success(response.body()?.results ?: mutableListOf())
                    }

                    override fun onFailure(call: Call<ResultListApi>, t: Throwable) {
                        liveDataAppState.value = AppState.Error(Exception(t))
                    }

                })
            }
        }
    }

    fun setIdMovieOpen(id: Int) {
        liveDataCurrentIdMovie.value = id
    }

    fun getMovieDetails(id: Int) {
        moviesRepository.getMovieDetail(id).enqueue(object: Callback<MovieDetails> {
            override fun onResponse(call: Call<MovieDetails>, response: Response<MovieDetails>) {
                liveDataCurrentMovie.value = AppState.Success(response.body() as MovieDetails)
            }

            override fun onFailure(call: Call<MovieDetails>, t: Throwable) {
                liveDataAppState.value = AppState.Error(Exception(t))
            }
        })
    }

    fun setLiveDataCurrentMovie(data: AppState<MovieDetails>) {
        liveDataCurrentMovie.value = data
    }

    fun addInFavorites(movieDetails: MovieDetails) {
        moviesRepository.addFavoriteMovie(movieDetails)
    }

    fun isFavoritesMovie(movieDetails: MovieDetails): Boolean {
        return moviesRepository.isFavoriteMovie(movieDetails)
    }

}
