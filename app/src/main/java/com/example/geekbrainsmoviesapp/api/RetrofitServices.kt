package com.example.geekbrainsmoviesapp.api

import com.example.geekbrainsmoviesapp.model.MovieDto
import com.example.geekbrainsmoviesapp.model.ResultListApi
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitServices {
    @GET("/3/movie/now_playing")
    fun getMoviesList(@Query("include_adult") isAdult: Boolean): Call<ResultListApi>

    @GET("/3/movie/top_rated")
    fun getRatingMoviesList(): Call<ResultListApi>

    @GET("/3/movie/{id}")
    fun getMovie(@Path("id") id: Int): Call<MovieDto>
}
