package com.example.moviesdemo.presentation.list

import androidx.lifecycle.viewModelScope
import com.example.moviesdemo.app.base.BaseViewModel
import com.example.moviesdemo.app.base.LiveDataState
import com.example.moviesdemo.app.utils.ConnectionManager
import com.example.moviesdemo.data.remote.AllMoviesResponse
import com.example.moviesdemo.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: MovieRepository,
    private val connectionManager: ConnectionManager
) :BaseViewModel(){

    val popularMoviesListResponse = LiveDataState<AllMoviesResponse>()

    fun refreshMovieList() {

        publishLoading(popularMoviesListResponse)

        if (!connectionManager.isNetworkAvailable){
            publishNoInternet(popularMoviesListResponse)
            return
        }

        viewModelScope.launch {
            val result = repository.getAllPopular()
            if (result.isSuccessful){
                delay(1000)
                publishResult(popularMoviesListResponse,result.body())
            }else{
                publishError(popularMoviesListResponse,result.message())
            }
        }
    }
}