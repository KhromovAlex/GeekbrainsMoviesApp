package com.example.geekbrainsmoviesapp.model

import java.util.*

data class Movie(
    var id: Int,
    var originalLanguage: String,
    var originalTitle: String,
    var title: String,
    var overview: String,
    var posterPath: String,
    var releaseDate: Date,
    var voteAverage: Double,
    var voteCount: Int,
    var genres: List<Genre>,
    var budget: Int,
    var revenue: Int,
    var runtime: Int,
)
