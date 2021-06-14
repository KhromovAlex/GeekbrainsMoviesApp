package com.example.geekbrainsmoviesapp.repository

import com.example.geekbrainsmoviesapp.api.RetrofitServices
import com.example.geekbrainsmoviesapp.common.Common
import com.example.geekbrainsmoviesapp.model.Movie
import com.example.geekbrainsmoviesapp.model.MovieDetails
import com.example.geekbrainsmoviesapp.model.ResultListApi
import retrofit2.Call

class MoviesRepositoryImpl : MoviesRepository {
    private val mService: RetrofitServices = Common.retrofitService
    private val favoritesList: MutableList<MovieDetails> = mutableListOf()

    override fun getAllMovies(): Call<ResultListApi> {
        return mService.getMoviesList()
    }

    override fun getFavoritesMovies(): List<Movie> {
        return favoritesList.map {
            Movie(
                id = it.id,
                originalTitle = it.originalTitle,
                title = it.title,
                overview = it.overview,
                posterPath = it.posterPath,
            )
        }
    }

    override fun addFavoriteMovie(movieDetails: MovieDetails) {
        if (favoritesList.contains(movieDetails)) {
            favoritesList.remove(movieDetails)
        } else {
            favoritesList.add(movieDetails)
        }
    }

    override fun isFavoriteMovie(movieDetails: MovieDetails): Boolean {
        return favoritesList.contains(movieDetails)
    }

    override fun getRatingsMovies(): Call<ResultListApi> {
        return mService.getRatingMoviesList()
    }

    override fun getMovieDetail(id: Int): Call<MovieDetails> {
        return mService.getMovie(id)
    }
}
