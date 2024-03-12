package com.example.moviesdemo.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("top_rated")
    suspend fun getAllTopRated(@Query("api_key") key: String): Response<AllMoviesResponse>

    @GET("popular")
    suspend fun getAllPopular(@Query("api_key") key: String): Response<AllMoviesResponse>

    @GET("now_playing")
    suspend fun getAllNowPlaying(@Query("api_key") key: String): Response<AllMoviesResponse>

    @GET("upcoming")
    suspend fun getAllUpcoming(@Query("api_key") key: String): Response<AllMoviesResponse>

    @GET("{movie_id}")
    suspend fun getMovieById(
        @Path("movie_id") movieId: String,
        @Query("api_key") key: String,
    ): Response<MovieResponse>

}