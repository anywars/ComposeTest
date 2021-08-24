package com.anypeace.mvi

import android.app.Application
import androidx.room.Database
import androidx.room.RoomDatabase
import com.anypeace.mvi.data.RepoDao
import com.anypeace.mvi.model.github.Repo
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class App: Application() {

    override fun onCreate() {
        super.onCreate()
    }

}


@Database(
    entities = [Repo::class],
    version = 1
)
abstract class AppDB: RoomDatabase() {
    abstract fun repoDao(): RepoDao
}