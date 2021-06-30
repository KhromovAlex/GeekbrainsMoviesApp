package com.example.geekbrainsmoviesapp.repository

import com.example.geekbrainsmoviesapp.model.MovieDto
import com.example.geekbrainsmoviesapp.room.dao.MovieDao
import com.example.geekbrainsmoviesapp.utils.movieDtoFromMovieEntity
import com.example.geekbrainsmoviesapp.utils.movieEntityFromMovieDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalDbRepositoryImpl(private val localDb: MovieDao) : LocalDbRepository {
    override suspend fun insertMovieAll(vararg movieDto: MovieDto) {
        localDb.insertAllMovie(*movieDto.map { movieEntityFromMovieDto(it) }.toTypedArray())
    }

    override fun getAllMovies(): Flow<List<MovieDto>> {
        return localDb.getAllMovies().map {
            it
                .map { movieEntity ->
                    movieDtoFromMovieEntity(movieEntity)
                }
        }
    }

    override fun getAllMoviesSkipAdult(): Flow<List<MovieDto>> {
        return localDb.getAllMoviesSkipAdult().map { list ->
            list
                .map { movieEntity ->
                    movieDtoFromMovieEntity(movieEntity)
                }
        }
    }

    override fun getFavoritesMovies(): Flow<List<MovieDto>> {
        return localDb.getFavoritesMovies().map {
            it.map { movieEntity ->
                movieDtoFromMovieEntity(movieEntity)
            }
        }
    }

    override fun getRatingsMovies(): Flow<List<MovieDto>> {
        return localDb.getRatingsMovies().map {
            it.map { movieEntity ->
                movieDtoFromMovieEntity(movieEntity)
            }
        }
    }

    override suspend fun getMovie(id: Int): MovieDto {
        return movieDtoFromMovieEntity(localDb.getMovie(id))
    }

    override suspend fun updateMovie(movieDto: MovieDto) {
        localDb.updateMovie(movieEntityFromMovieDto(movieDto))
    }

}
