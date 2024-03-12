package com.example.moviesdemo.data.repository

import com.example.moviesdemo.data.remote.MovieService
import javax.inject.Inject

class MovieRepository @Inject constructor(private val service: MovieService) {
}