package com.example.geekbrainsmoviesapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geekbrainsmoviesapp.App.Companion.getMovieDao
import com.example.geekbrainsmoviesapp.model.AppState
import com.example.geekbrainsmoviesapp.model.MovieDto
import com.example.geekbrainsmoviesapp.model.MoviesFilter
import com.example.geekbrainsmoviesapp.model.ResultListApi
import com.example.geekbrainsmoviesapp.repository.LocalDbRepository
import com.example.geekbrainsmoviesapp.repository.LocalDbRepositoryImpl
import com.example.geekbrainsmoviesapp.repository.MoviesRepository
import com.example.geekbrainsmoviesapp.repository.MoviesRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesListViewModel : ViewModel() {
    private val moviesRepository: MoviesRepository = MoviesRepositoryImpl()
    private val localDbRepository: LocalDbRepository = LocalDbRepositoryImpl(getMovieDao())
    private val liveDataAppState: MutableLiveData<AppState<List<MovieDto>>> = MutableLiveData()
    private val liveDataCurrentMovieDto: MutableLiveData<AppState<MovieDto>> = MutableLiveData()
    private val liveDataCurrentIdMovie: MutableLiveData<Int> = MutableLiveData()

    fun getLiveDataAppState() = liveDataAppState

    fun getLiveDataCurrentMovie() = liveDataCurrentMovieDto

    fun getLiveDataCurrentIdMovie() = liveDataCurrentIdMovie

    fun loadMovies(moviesFilter: MoviesFilter) {
        when (moviesFilter) {
            MoviesFilter.All -> {
                moviesRepository.getAllMovies(true).enqueue(object : Callback<ResultListApi> {
                    override fun onResponse(
                        call: Call<ResultListApi>,
                        response: Response<ResultListApi>
                    ) {
                        viewModelScope.launch(Dispatchers.IO) {
                            response.body()?.results?.let { list ->
                                localDbRepository.insertMovieAll(
                                    *list.map {
                                        it.apply {
                                            filter = "All"
                                        }
                                    }.toTypedArray()
                                )
                            }
                        }
                    }

                    override fun onFailure(call: Call<ResultListApi>, t: Throwable) {
                        liveDataAppState.postValue(AppState.Error(Exception(t)))
                    }

                })
            }
            MoviesFilter.Favorites -> {
            }
            MoviesFilter.Rating -> {
                moviesRepository.getRatingsMovies().enqueue(object : Callback<ResultListApi> {
                    override fun onResponse(
                        call: Call<ResultListApi>,
                        response: Response<ResultListApi>
                    ) {
                        viewModelScope.launch(Dispatchers.IO) {
                            response.body()?.results?.let { list ->
                                localDbRepository.insertMovieAll(
                                    *list.map { it.apply { filter = "Rating" } }.toTypedArray()
                                )
                            }
                        }
                    }

                    override fun onFailure(call: Call<ResultListApi>, t: Throwable) {
                        liveDataAppState.postValue(AppState.Error(Exception(t)))
                    }

                })
            }
            MoviesFilter.SkipAdult -> {
                moviesRepository.getAllMovies(false).enqueue(object : Callback<ResultListApi> {
                    override fun onResponse(
                        call: Call<ResultListApi>,
                        response: Response<ResultListApi>
                    ) {
                        viewModelScope.launch(Dispatchers.IO) {
                            response.body()?.results?.let { list ->
                                localDbRepository.insertMovieAll(
                                    *list
                                        .map { it.apply { filter = "All" } }
                                        .toTypedArray()
                                )
                            }
                        }
                    }

                    override fun onFailure(call: Call<ResultListApi>, t: Throwable) {
                        liveDataAppState.postValue(AppState.Error(Exception(t)))
                    }

                })
            }
        }
    }

    fun getMovies(moviesFilter: MoviesFilter) {
        when (moviesFilter) {
            MoviesFilter.All -> {
                liveDataAppState.value = AppState.Loading()

                viewModelScope.launch(Dispatchers.IO) {
                    localDbRepository.getAllMovies()
                        .catch { exception ->
                            liveDataAppState.postValue(
                                AppState.Error(
                                    Exception(
                                        exception
                                    )
                                )
                            )
                        }
                        .collect {
                            liveDataAppState.postValue(AppState.Success(it))
                        }
                }
            }
            MoviesFilter.Favorites -> {
                liveDataAppState.value = AppState.Loading()

                viewModelScope.launch(Dispatchers.IO) {
                    localDbRepository.getFavoritesMovies()
                        .catch { exception ->
                            liveDataAppState.postValue(
                                AppState.Error(
                                    Exception(
                                        exception
                                    )
                                )
                            )
                        }
                        .collect {
                            liveDataAppState.postValue(AppState.Success(it))
                        }
                }
            }
            MoviesFilter.Rating -> {
                liveDataAppState.value = (AppState.Loading())
                viewModelScope.launch(Dispatchers.IO) {
                    localDbRepository.getRatingsMovies()
                        .catch { exception ->
                            liveDataAppState.postValue(AppState.Error(Exception(exception)))
                        }
                        .collect {
                            liveDataAppState.postValue(AppState.Success(it))
                        }
                }
            }
            MoviesFilter.SkipAdult -> {
                liveDataAppState.value = AppState.Loading()

                viewModelScope.launch(Dispatchers.IO) {
                    localDbRepository.getAllMoviesSkipAdult()
                        .catch { exception ->
                            liveDataAppState.postValue(
                                AppState.Error(
                                    Exception(
                                        exception.message
                                    )
                                )
                            )
                        }
                        .collect {
                            liveDataAppState.postValue(AppState.Success(it))
                        }
                }
            }
        }
    }

    fun setIdMovieOpen(id: Int) {
        liveDataCurrentIdMovie.value = id
    }

    fun getMovieDetails(id: Int) {
        moviesRepository.getMovieDetail(id).enqueue(object : Callback<MovieDto> {
            override fun onResponse(call: Call<MovieDto>, response: Response<MovieDto>) {
                liveDataCurrentMovieDto.value = AppState.Success(response.body() as MovieDto)
            }

            override fun onFailure(call: Call<MovieDto>, t: Throwable) {
                liveDataAppState.value = AppState.Error(Exception(t))
            }
        })
    }

    fun setLiveDataCurrentMovie(data: AppState<MovieDto>) {
        liveDataCurrentMovieDto.value = data
    }

    fun setLiveDataAppState(data: AppState<List<MovieDto>>) {
        liveDataAppState.value = data
    }

    fun toggleFavorites(movieDto: MovieDto) {
        viewModelScope.launch(Dispatchers.IO) {
            localDbRepository.updateMovie(movieDto)
        }
    }

    fun loadMovie(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            liveDataCurrentMovieDto.postValue(AppState.Success(localDbRepository.getMovie(id)))
        }
    }

    fun updateMovie(movieDto: MovieDto) {
        viewModelScope.launch(Dispatchers.IO) {
            val oldMovie = localDbRepository.getMovie(movieDto.id)
            val updateMovie = movieDto.apply {
                isFavorites = oldMovie.isFavorites
                filter = if (filter == null) oldMovie.filter else filter
                note = if (note == null) oldMovie.note else note
            }
            localDbRepository.updateMovie(updateMovie)
            loadMovie(updateMovie.id)
        }
    }

}
