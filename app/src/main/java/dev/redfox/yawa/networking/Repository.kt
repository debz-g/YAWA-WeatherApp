package dev.redfox.yawa.networking

import dev.redfox.yawa.model.WeatherResponse
import retrofit2.Response
import retrofit2.Retrofit

class Repository {

    suspend fun getData(lat: Double, lon: Double, appid: String, ): Response<WeatherResponse>{
        return RetrofitInstance.api.getData(lat, lon, appid)
    }
}