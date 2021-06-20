package com.example.geekbrainsmoviesapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class MovieDto(
    @SerializedName("id")
    var id: Int,
    @SerializedName("original_language")
    var originalLanguage: String? = null,
    @SerializedName("original_title")
    var originalTitle: String,
    @SerializedName("title")
    var title: String,
    @SerializedName("overview")
    var overview: String,
    @SerializedName("poster_path")
    var posterPath: String?,
    @SerializedName("release_date")
    var releaseDate: Date? = null,
    @SerializedName("vote_average")
    var voteAverage: Double? = null,
    @SerializedName("vote_count")
    var voteCount: Int? = null,
    @SerializedName("budget")
    var budget: Int? = null,
    @SerializedName("revenue")
    var revenue: Int? = null,
    @SerializedName("runtime")
    var runtime: Int? = null,
    @SerializedName("adult")
    var adult: Boolean,
    var filter: String?,
    var isFavorites: Boolean = false,
    var note: String?
) : Parcelable
