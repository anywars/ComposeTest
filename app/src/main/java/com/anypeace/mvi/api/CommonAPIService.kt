package com.anypeace.mvi.api

import com.anypeace.mvi.model.Result
import com.anypeace.mvi.model.Version
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST


interface CommonAPIService {

    @POST("/api/common/v1/version.json")
    fun version(
        @Body version: Version
    ): Observable<Result<Version>>

}