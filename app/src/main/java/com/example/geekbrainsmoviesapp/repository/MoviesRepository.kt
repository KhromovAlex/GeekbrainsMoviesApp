package com.example.geekbrainsmoviesapp.repository

import com.example.geekbrainsmoviesapp.model.Movie
import com.example.geekbrainsmoviesapp.model.MovieDetails
import com.example.geekbrainsmoviesapp.model.ResultListApi
import retrofit2.Call

interface MoviesRepository {
    fun getAllMovies(): Call<ResultListApi>

    fun getFavoritesMovies(): List<Movie>

    fun getRatingsMovies(): Call<ResultListApi>

    fun getMovieDetail(id: Int): Call<MovieDetails>

    fun addFavoriteMovie(movieDetails: MovieDetails)

    fun isFavoriteMovie(movieDetails: MovieDetails): Boolean
}
