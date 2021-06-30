package com.example.geekbrainsmoviesapp.common

import com.example.geekbrainsmoviesapp.api.RetrofitClient
import com.example.geekbrainsmoviesapp.api.RetrofitServices

object Common {
    private const val BASE_URL = "https://api.themoviedb.org/"
    const val BASE_URL_IMAGE = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/"
    private const val API_KEY =
        "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyNDhiNTFjMTUzYzBlNDdlYjA2YTFiMGY5MGQ5MmIwYiIsInN1YiI6IjYwYThlZmZkODU3MDJlMDA0MDYyYWM0MCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.zlp7PWKiVkvcwieFRHTA4wlOiQAHazPRkl6C3qb6-xw"
    const val DETAILS_INTENT_FILTER = "DETAILS_INTENT_FILTER"
    const val DETAILS_LOAD_RESULT_EXTRA = "DETAILS_LOAD_RESULT_EXTRA"
    const val DETAILS_RESULT_NULL_INTENT = "DETAILS_RESULT_NULL_INTENT"
    const val DETAILS_RESULT_SUCCESS = "DETAILS_RESULT_SUCCESS"
    const val DETAILS_RESULT_ERROR = "DETAILS_RESULT_ERROR"
    const val DETAILS_REQUEST_ID_EXTRA = "DETAILS_INTENT_FILTER"
    const val DETAILS_REQUEST_DATA = "DETAILS_REQUEST_DATA"

    val retrofitService: RetrofitServices
        get() = RetrofitClient.getClient(BASE_URL, API_KEY).create(RetrofitServices::class.java)
}
