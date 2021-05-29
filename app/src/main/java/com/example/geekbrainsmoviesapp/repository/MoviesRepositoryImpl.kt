package com.example.geekbrainsmoviesapp.repository

import com.example.geekbrainsmoviesapp.model.Movie

class MoviesRepositoryImpl : MoviesRepository {

    override fun getAllMovies(): List<Movie> {
        return arrayListOf()
    }

    override fun getFavoritesMovies(): List<Movie> {
        return arrayListOf()
    }

    override fun getRatingsMovies(): List<Movie> {
        return arrayListOf()
    }
}
