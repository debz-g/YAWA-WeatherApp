package dev.redfox.yawa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import dev.redfox.yawa.databinding.ActivityMainBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dev.redfox.yawa.api.Repository
import dev.redfox.yawa.utils.Constants.Constants.Companion.APP_ID
import dev.redfox.yawa.viewmodel.WeatherViewModel
import dev.redfox.yawa.viewmodel.WeatherViewModelFactory
private lateinit var binding: ActivityMainBinding
private lateinit var viewModel: WeatherViewModel
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnSearch.setOnClickListener {
        val city=binding.etQuery.text.toString()
            viewModel.getData(city, APP_ID)
        }
        val repository = Repository()
        val viewModelFactory = WeatherViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(WeatherViewModel::class.java)

        viewModel.wResponse.observe(this, Observer {
            binding.tempText.text = ((it.body()!!.main.temp)-273.15).toInt().toString()+"°C"
            binding.tvCityName.text = binding.etQuery.text.toString().uppercase()
            binding.tvDescription.text = it.body()!!.weather[0].description.uppercase()
        })

    }

}