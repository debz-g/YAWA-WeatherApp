package dev.redfox.yawa

import androidx.lifecycle.*
import dev.redfox.yawa.model.WeatherResponse
import dev.redfox.yawa.networking.Repository
import dev.redfox.yawa.networking.WeatherAPI
import dev.redfox.yawa.utils.Constants
import dev.redfox.yawa.utils.Constants.Companion.APP_ID
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: Repository): ViewModel() {

    private val _wResponse = MutableLiveData<Response<WeatherResponse>>()
    val wResponse: LiveData<Response<WeatherResponse>> get() = _wResponse

    fun getData(lat: Double, lon: Double,appid: String){
        viewModelScope.launch {
            val response = repository.getData(lat, lon, appid)
            _wResponse.value = response
        }
    }
}

class MainViewModelFactory(val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}

