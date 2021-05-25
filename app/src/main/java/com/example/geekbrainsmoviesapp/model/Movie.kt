package com.example.geekbrainsmoviesapp.model

data class Movie(
    var id: Int,
    var originalLanguage: String,
    var originalTitle: String,
    var title: String,
    var overview: String,
    var posterPath: String,
    var releaseDate: String,
    var voteAverage: Int,
)
