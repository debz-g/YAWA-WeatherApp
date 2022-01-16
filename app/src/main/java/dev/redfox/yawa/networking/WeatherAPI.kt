package dev.redfox.yawa.networking

import dev.redfox.yawa.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    @GET("data/2.5/weather?")
    suspend fun getData(
        @Query("appid") appid: String,
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): Response<WeatherResponse>
}