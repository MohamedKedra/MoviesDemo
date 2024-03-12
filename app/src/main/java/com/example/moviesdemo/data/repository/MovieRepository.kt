package com.example.moviesdemo.data.repository

import com.example.moviesdemo.app.utils.Constant
import com.example.moviesdemo.data.remote.MovieService
import javax.inject.Inject

class MovieRepository @Inject constructor(private val service: MovieService) {

    suspend fun getTopRatedMovies() = service.getAllTopRated(Constant.apiKey)
    suspend fun getAllUpcoming() = service.getAllUpcoming(Constant.apiKey)
    suspend fun getAllPopular() = service.getAllPopular(Constant.apiKey)
    suspend fun getAllNowPlaying() = service.getAllNowPlaying(Constant.apiKey)
    suspend fun getMovieById(id:String) = service.getMovieById(id,Constant.apiKey)
}