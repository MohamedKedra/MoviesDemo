package com.example.moviesdemo.data.remote

import androidx.room.Entity
import androidx.room.PrimaryKey

data class AllMoviesResponse(
    val dates: Dates? = null,
    val page: Int? = null,
    val results: ArrayList<Result>? = null,
    val total_pages: Int? = null,
    val total_results: Int? = null
)

data class Dates(
    val maximum: String? = null,
    val minimum: String? = null
)

@Entity(tableName = "movies")
data class Result(
    val adult: Boolean? = null,
    val backdrop_path: String? = null,
    @PrimaryKey
    val id: Int? = null,
    val original_language: String? = null,
    val original_title: String? = null,
    val overview: String? = null,
    val popularity: Double? = null,
    val poster_path: String? = null,
    val release_date: String? = null,
    val title: String? = null,
    val video: Boolean? = null,
    val vote_average: Double? = null,
    val vote_count: Int? = null
)