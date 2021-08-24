package com.anypeace.mvi.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.anypeace.mvi.model.github.Repo
import io.reactivex.Observable

@Dao
interface RepoDao {

    @Query("SELECT * FROM repo WHERE id = :id")
    fun findById(id: Int): Observable<Repo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(vararg repo: Repo)

}