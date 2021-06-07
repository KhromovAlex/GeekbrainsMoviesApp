package com.example.geekbrainsmoviesapp.common

import com.example.geekbrainsmoviesapp.api.RetrofitClient
import com.example.geekbrainsmoviesapp.api.RetrofitServices

object Common {
    const val BASE_URL = "https://api.themoviedb.org/"
    const val BASE_URL_IMAGE = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/"
    const val API_KEY = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyNDhiNTFjMTUzYzBlNDdlYjA2YTFiMGY5MGQ5MmIwYiIsInN1YiI6IjYwYThlZmZkODU3MDJlMDA0MDYyYWM0MCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.zlp7PWKiVkvcwieFRHTA4wlOiQAHazPRkl6C3qb6-xw"
    val retrofitService: RetrofitServices
        get() = RetrofitClient.getClient(BASE_URL, API_KEY).create(RetrofitServices::class.java)
}
