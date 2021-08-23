package kr.co.jsongroup.composetest

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object Repository {

    @Provides
    @Singleton
    fun service(): CommonAPIService {
        val client = OkHttpClient.Builder()
            .build()

        return Retrofit.Builder()
            .baseUrl("http://192.168.102.199:9090")
            .addConverterFactory(JacksonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
            .create(CommonAPIService::class.java)
    }

}


interface CommonAPIService {

    @POST("/api/common/v1/version.json")
    fun version(@Body version: Version): Observable<Result<Version>>

}

data class Result <T>(
    var resultCd: String? = null,
    var msg: String? = null,
    var payload: T? = null,
)

data class Version(
    var version: String? = null,
    var osType: String? = null,
    var memo: String? = null,
    var marketUrl: String? = null,
    var updateFlag: String? = null
)