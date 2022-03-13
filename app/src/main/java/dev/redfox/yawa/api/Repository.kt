package dev.redfox.yawa.api

import dev.redfox.yawa.databinding.ActivityMainBinding
import retrofit2.Response

class Repository {
    suspend fun getData(city:String, appid: String ): Response<WeatherItem>{
        return RetrofitInstance.api.getData(city,appid)
    }
}