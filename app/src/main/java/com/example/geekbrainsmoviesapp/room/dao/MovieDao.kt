package com.example.geekbrainsmoviesapp.room.dao

import androidx.room.*
import com.example.geekbrainsmoviesapp.room.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllMovie(vararg movieEntity: MovieEntity)

    @Query("SELECT * FROM MovieEntity WHERE filter = 'All'")
    fun getAllMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM MovieEntity WHERE filter = 'All' AND adult = 0")
    fun getAllMoviesSkipAdult(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM MovieEntity WHERE isFavorites = 1")
    fun getFavoritesMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM MovieEntity WHERE filter = 'Rating'")
    fun getRatingsMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM MovieEntity WHERE id = :id")
    suspend fun getMovie(id: Int): MovieEntity

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun updateMovie(movieEntity: MovieEntity)

}
