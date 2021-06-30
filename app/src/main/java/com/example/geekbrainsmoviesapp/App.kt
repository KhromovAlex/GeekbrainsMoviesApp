package com.example.geekbrainsmoviesapp

import android.app.Application
import androidx.room.Room
import com.example.geekbrainsmoviesapp.room.LocalDatabase
import com.example.geekbrainsmoviesapp.room.dao.MovieDao

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {

        private var appInstance: App? = null
        private var db: LocalDatabase? = null
        private const val DB_NAME = "Local.db"

        fun getMovieDao(): MovieDao {
            if (db == null) {
                synchronized(LocalDatabase::class.java) {
                    if (db == null) {
                        if (appInstance == null) throw IllegalStateException("Application is null while creating DataBase")
                        db = Room.databaseBuilder(
                            appInstance!!.applicationContext,
                            LocalDatabase::class.java,
                            DB_NAME
                        )
                            .build()
                    }
                }
            }

            return db!!.movieDao()
        }
    }
}
