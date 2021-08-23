package com.anypeace.mvi.api

import com.anypeace.mvi.model.github.RepoSearchResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface GithubAPIService {

    @GET("search/repositories?sort=stars")
    fun searchRepos(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int,
    ): Observable<RepoSearchResult>

}