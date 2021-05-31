package com.example.geekbrainsmoviesapp.repository

import com.example.geekbrainsmoviesapp.model.Genre
import com.example.geekbrainsmoviesapp.model.Movie
import java.util.*

class MoviesRepositoryImpl : MoviesRepository {

    override fun getAllMovies(): List<Movie> {
        val movie = Movie(
            id = 0,
            originalLanguage = "ru",
            originalTitle = "Джокер",
            title = "Джокер",
            overview = "Готэм, начало 1980-х годов. Комик Артур Флек живёт с больной матерью, которая с детства учит его «ходить с улыбкой». Пытаясь нести в мир хорошее и дарить людям радость, Артур сталкивается с человеческой жестокостью и постепенно приходит к выводу, что этот мир получит от него не добрую улыбку, а ухмылку злодея Джокера.",
            posterPath = "",
            releaseDate = Date(1570060800000),
            voteAverage = 8.4,
            voteCount = 7054,
            runtime = 139,
            budget = 2353008,
            revenue = 956200,
            genres = mutableListOf(
                Genre(id = 18, name = "Drama")
            )
        )

        return mutableListOf(
            movie.copy(id = 1),
            movie.copy(id = 2),
            movie.copy(id = 3),
            movie.copy(id = 4),
            movie.copy(id = 5),
            movie.copy(id = 6),
            movie.copy(id = 7),
        )
    }

    override fun getFavoritesMovies(): List<Movie> {
        val movie = Movie(
            id = 0,
            originalLanguage = "en",
            originalTitle = "Game of Thrones",
            title = "Игра престолов",
            overview = "К концу подходит время благоденствия, и лето, длившееся почти десятилетие, угасает. Вокруг средоточия власти Семи Королевств, Железного трона, зреет заговор, и в это непростое время король решает искать поддержки у друга юности Эддарда Старка. В мире, где все — от короля до наёмника — рвутся к власти, плетут интриги и готовы вонзить нож в спину, есть место и благородству, состраданию и любви. Между тем, никто не замечает пробуждение тьмы из легенд далеко на Севере — и лишь Стена защищает живых к югу от неё.",
            posterPath = "",
            releaseDate = Date(1570060800000),
            voteAverage = 9.4,
            voteCount = 9805,
            runtime = 139,
            budget = 2353008,
            revenue = 956200,
            genres = mutableListOf(
                Genre(id = 18, name = "Drama")
            )
        )

        return mutableListOf(
            movie.copy(id = 1),
            movie.copy(id = 2),
            movie.copy(id = 3),
            movie.copy(id = 4),
            movie.copy(id = 5),
            movie.copy(id = 6),
            movie.copy(id = 7),
        )
    }

    override fun getRatingsMovies(): List<Movie> {
        val movie = Movie(
            id = 0,
            originalLanguage = "en",
            originalTitle = "Vikings",
            title = "Викинги",
            overview = "Сериал рассказывает об отряде викингов Рагнара Лодброка. Он восстал, чтобы стать королём племён викингов. Норвежская легенда гласит, что он был прямым потомком Одина, бога войны и воинов.",
            posterPath = "",
            releaseDate = Date(1570060800000),
            voteAverage = 8.4,
            voteCount = 7054,
            runtime = 139,
            budget = 2353008,
            revenue = 956200,
            genres = mutableListOf(
                Genre(id = 18, name = "Drama")
            )
        )

        return mutableListOf(
            movie.copy(id = 1),
            movie.copy(id = 2),
            movie.copy(id = 3),
            movie.copy(id = 4),
            movie.copy(id = 5),
            movie.copy(id = 6),
            movie.copy(id = 7),
        )
    }
}
