package dev.redfox.yawa.networking

import dev.redfox.yawa.model.WeatherResponse
import retrofit2.Response
import retrofit2.Retrofit

class Repository {

    suspend fun getWeatherData(appid: String, lat: Double, lon: Double): Response<WeatherResponse>{
        return RetrofitInstance.api.getData(appid,lat,lon)
    }
}