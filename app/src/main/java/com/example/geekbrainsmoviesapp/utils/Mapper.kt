package com.example.geekbrainsmoviesapp.utils

import com.example.geekbrainsmoviesapp.model.MovieDto
import com.example.geekbrainsmoviesapp.room.entity.MovieEntity
import java.util.*

fun movieEntityFromMovieDto(movieDto: MovieDto): MovieEntity {
    return MovieEntity(
        id = movieDto.id,
        originalLanguage = movieDto.originalLanguage,
        originalTitle = movieDto.originalTitle,
        title = movieDto.title,
        overview = movieDto.overview,
        posterPath = movieDto.posterPath,
        releaseDate = movieDto.releaseDate.toString(),
        voteAverage = movieDto.voteAverage,
        voteCount = movieDto.voteCount,
        budget = movieDto.budget,
        revenue = movieDto.revenue,
        runtime = movieDto.runtime,
        adult = movieDto.adult,
        filter = movieDto.filter,
        isFavorites = movieDto.isFavorites,
        note = movieDto.note,
    )
}

fun movieDtoFromMovieEntity(movieEntity: MovieEntity): MovieDto {
    return MovieDto(
        id = movieEntity.id,
        originalLanguage = movieEntity.originalLanguage,
        originalTitle = movieEntity.originalTitle,
        title = movieEntity.title,
        overview = movieEntity.overview,
        posterPath = movieEntity.posterPath,
        releaseDate = Date(movieEntity.releaseDate),
        voteAverage = movieEntity.voteAverage,
        voteCount = movieEntity.voteCount,
        budget = movieEntity.budget,
        revenue = movieEntity.revenue,
        runtime = movieEntity.runtime,
        adult = movieEntity.adult,
        filter = movieEntity.filter,
        isFavorites = movieEntity.isFavorites,
        note = movieEntity.note,
    )
}
