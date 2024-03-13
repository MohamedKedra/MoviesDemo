package com.example.moviesdemo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moviesdemo.data.remote.Result

@Database(entities = [Result::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
}