package dev.redfox.yawa.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface Api {
    @GET("data/2.5/weather")
    suspend fun getData(
        @Query("q") city: String,
        @Query("appid") appid: String
    ): Response<WeatherItem>
}