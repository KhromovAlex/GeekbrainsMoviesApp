package com.example.geekbrainsmoviesapp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.geekbrainsmoviesapp.room.dao.MovieDao
import com.example.geekbrainsmoviesapp.room.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

}
