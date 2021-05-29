package com.example.geekbrainsmoviesapp.repository

import com.example.geekbrainsmoviesapp.model.Movie

interface MoviesRepository {
    fun getAllMovies(): List<Movie>

    fun getFavoritesMovies(): List<Movie>

    fun getRatingsMovies(): List<Movie>
}
