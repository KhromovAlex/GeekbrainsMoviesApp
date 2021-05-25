package com.example.geekbrainsmoviesapp.model

sealed class AppState<T> {
    data class Success<T>(val data: T) : AppState<T>()
    data class Error<T>(val error: Exception) : AppState<T>()
    class Loading<T> : AppState<T>()
}
