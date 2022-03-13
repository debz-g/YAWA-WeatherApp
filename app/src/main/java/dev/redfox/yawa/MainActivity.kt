package dev.redfox.yawa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.redfox.yawa.databinding.ActivityMainBinding
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.location.LocationRequest
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.location.LocationManagerCompat.isLocationEnabled
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import dev.redfox.yawa.networking.Repository
import dev.redfox.yawa.utils.Constants.Companion.APP_ID

private lateinit var binding: ActivityMainBinding
private lateinit var viewModel: MainViewModel
class MainActivity : AppCompatActivity() {

    private var reqCode = 1010
    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var lat = intent.getDoubleExtra("lat",0.00)
        val lon = intent.getDoubleExtra("lon",0.00)

        binding.swipeToRefresh.setOnRefreshListener {
            binding.swipeToRefresh.isRefreshing = false
        }

        val repository =Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getData(lat, lon, APP_ID)
        viewModel.wResponse.observe(this, Observer {
            binding.tempText.text = it.body()!!.main.temp.toString()
            binding.tvCity.text = it.body()!!.name
            binding.tvWeatherDesc.text = it.body()!!.weather[0].description
        })
    }
}