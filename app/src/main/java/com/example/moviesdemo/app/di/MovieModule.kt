package com.example.moviesdemo.app.di

import android.content.Context
import androidx.room.Room
import com.example.moviesdemo.app.utils.ConnectionManager
import com.example.moviesdemo.app.utils.Constant
import com.example.moviesdemo.data.local.MovieDao
import com.example.moviesdemo.data.local.MovieDatabase
import com.example.moviesdemo.data.remote.MovieService
import com.example.moviesdemo.data.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MovieModule {
    @Singleton
    @Provides
    fun providesRetrofit() = Retrofit.Builder()
        .baseUrl(Constant.baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideService(retrofit: Retrofit) = retrofit.create(MovieService::class.java)

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext appContext: Context) =
        Room.databaseBuilder(appContext, MovieDatabase::class.java, "moviesDB")
            .allowMainThreadQueries()
            .build()

    @Singleton
    @Provides
    fun provideDao(movieDatabase: MovieDatabase) = movieDatabase.movieDao()

    @Singleton
    @Provides
    fun provideRepository(service: MovieService, dao: MovieDao) = MovieRepository(service, dao)

    @Singleton
    @Provides
    fun provideNetwork(@ApplicationContext context: Context) =
        ConnectionManager(context)
}