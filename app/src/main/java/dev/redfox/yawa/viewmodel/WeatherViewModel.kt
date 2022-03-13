package dev.redfox.yawa.viewmodel

import androidx.lifecycle.*
import dev.redfox.yawa.api.Repository
import dev.redfox.yawa.api.WeatherItem
import kotlinx.coroutines.launch
import retrofit2.Response

class WeatherViewModel(private val repository: Repository):ViewModel() {
    private val _wResponse = MutableLiveData<Response<WeatherItem>>()
    val wResponse: LiveData<Response<WeatherItem>> get()= _wResponse
    fun getData(city:String, appid: String){
        viewModelScope.launch {
            val response = repository.getData(city, appid)
            _wResponse.value = response
        }
    }
}

class WeatherViewModelFactory(val repository: Repository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WeatherViewModel(repository) as T
    }
}