package com.example.moviesdemo.data.workmanager

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.moviesdemo.data.local.MovieDao
import com.example.moviesdemo.data.local.MovieDatabase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import javax.inject.Inject

@HiltWorker
class MovieWorker @AssistedInject constructor(
     @Assisted val context: Context,
     @Assisted workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        println("output initial")
        val dao = MovieDatabase.getInstance(context).movieDao()
        dao.deleteAll()
        return Result.success()
    }
}