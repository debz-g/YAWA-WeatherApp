package dev.redfox.yawa

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.redfox.yawa.networking.Repository

class MainViewModelFactory(val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}