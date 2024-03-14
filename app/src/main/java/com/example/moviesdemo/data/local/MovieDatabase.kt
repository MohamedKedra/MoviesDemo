package com.example.moviesdemo.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.moviesdemo.data.remote.Result

@Database(entities = [Result::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: MovieDatabase? = null

        fun getInstance(context: Context): MovieDatabase {
            return buildDatabase(context)
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, MovieDatabase::class.java, "moviesDB")
                .allowMainThreadQueries()
                .build()
    }
}