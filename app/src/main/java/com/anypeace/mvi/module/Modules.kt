package com.anypeace.mvi.module

import android.content.Context
import androidx.room.Room
import com.anypeace.mvi.AppDB
import com.anypeace.mvi.api.CommonAPIService
import com.anypeace.mvi.api.GithubAPIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object Repository {

    @Provides
    @Singleton
    fun logger(): HttpLoggingInterceptor {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BASIC
        return logger
    }

    @Provides
    @Singleton
    fun database(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AppDB::class.java, "mvi.db")
            .fallbackToDestructiveMigration()
            .build()


    @Provides
    @Singleton
    fun repoDao(db: AppDB) =
        db.repoDao()

}


@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    fun service(logger: HttpLoggingInterceptor): CommonAPIService {
        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()

        return Retrofit.Builder()
            .baseUrl("http://192.168.102.199:9090")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
            .create(CommonAPIService::class.java)
    }

    @Provides
    fun githubService(logger: HttpLoggingInterceptor): GithubAPIService {
        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
            .create(GithubAPIService::class.java)
    }

}