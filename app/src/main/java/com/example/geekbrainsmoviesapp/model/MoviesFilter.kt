package com.example.geekbrainsmoviesapp.model

sealed class MoviesFilter {
    object All : MoviesFilter()
    object SkipAdult : MoviesFilter()
    object Favorites : MoviesFilter()
    object Rating : MoviesFilter()
}
