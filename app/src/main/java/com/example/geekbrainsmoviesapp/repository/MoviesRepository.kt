package com.example.geekbrainsmoviesapp.repository

import com.example.geekbrainsmoviesapp.model.MovieDto
import com.example.geekbrainsmoviesapp.model.ResultListApi
import retrofit2.Call

interface MoviesRepository {
    fun getAllMovies(isAdult: Boolean): Call<ResultListApi>

    fun getRatingsMovies(): Call<ResultListApi>

    fun getMovieDetail(id: Int): Call<MovieDto>
}
