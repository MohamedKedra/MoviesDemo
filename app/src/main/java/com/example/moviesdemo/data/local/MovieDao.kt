package com.example.moviesdemo.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviesdemo.data.remote.Result

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun cacheAll(movies : ArrayList<Result>)

    @Query("SELECT * from movies")
    fun getCachedMovies(): List<Result>

    @Query("DELETE FROM movies")
    fun deleteAll()
}