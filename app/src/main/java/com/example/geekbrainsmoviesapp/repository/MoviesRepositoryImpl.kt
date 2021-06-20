package com.example.geekbrainsmoviesapp.repository

import com.example.geekbrainsmoviesapp.api.RetrofitServices
import com.example.geekbrainsmoviesapp.common.Common
import com.example.geekbrainsmoviesapp.model.MovieDto
import com.example.geekbrainsmoviesapp.model.ResultListApi
import retrofit2.Call

class MoviesRepositoryImpl : MoviesRepository {
    private val mService: RetrofitServices = Common.retrofitService

    override fun getAllMovies(isAdult: Boolean): Call<ResultListApi> {
        return mService.getMoviesList(isAdult)
    }

    override fun getRatingsMovies(): Call<ResultListApi> {
        return mService.getRatingMoviesList()
    }

    override fun getMovieDetail(id: Int): Call<MovieDto> {
        return mService.getMovie(id)
    }
}
