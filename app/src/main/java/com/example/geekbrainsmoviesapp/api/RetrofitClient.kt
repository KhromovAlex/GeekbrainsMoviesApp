package com.example.geekbrainsmoviesapp.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var retrofit: Retrofit? = null

    fun getClient(baseUrl: String, apiKey: String): Retrofit {
        if (retrofit == null) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client: OkHttpClient.Builder = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor {
                    it.proceed(
                        it.request().newBuilder().addHeader("Authorization", "Bearer $apiKey")
                            .addHeader("Content-Type", "application/x-www-form-urlencoded").build()
                    )
                }
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build()
        }
        return retrofit!!
    }
}
