package com.example.geekbrainsmoviesapp.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class MovieEntity (
    @PrimaryKey(autoGenerate = false)
    var id: Int,
    var originalLanguage: String? = null,
    var originalTitle: String,
    var title: String,
    var overview: String,
    var posterPath: String? = null,
    var releaseDate: String? = null,
    var voteAverage: Double? = null,
    var voteCount: Int? = null,
    var budget: Int? = null,
    var revenue: Int? = null,
    var runtime: Int? = null,
    var adult: Boolean,
    var note: String? = null,
    var filter: String? = null,
    var isFavorites: Boolean,
)
