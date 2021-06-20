package com.example.geekbrainsmoviesapp.repository

import com.example.geekbrainsmoviesapp.model.MovieDto
import kotlinx.coroutines.flow.Flow

interface LocalDbRepository {

    suspend fun insertMovieAll(vararg movieDto: MovieDto)

    fun getAllMovies(): Flow<List<MovieDto>>

    fun getAllMoviesSkipAdult(): Flow<List<MovieDto>>

    fun getFavoritesMovies(): Flow<List<MovieDto>>

    fun getRatingsMovies(): Flow<List<MovieDto>>

    suspend fun getMovie(id: Int): MovieDto

    suspend fun updateMovie(movieDto: MovieDto)

}
