package dev.redfox.yawa

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dev.redfox.yawa.model.WeatherResponse
import dev.redfox.yawa.networking.Repository
import dev.redfox.yawa.networking.WeatherAPI
import dev.redfox.yawa.utils.Constants
import dev.redfox.yawa.utils.Constants.Companion.APP_ID
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: Repository): ViewModel() {

    var wResponse: MutableLiveData<Response<WeatherResponse>> = MutableLiveData()

    fun getWeatherData(appid: String, lat: Double, lon: Double){
        viewModelScope.launch {
            val response = repository.getWeatherData(appid, lat, lon)
            wResponse.value = response
        }
    }
}

