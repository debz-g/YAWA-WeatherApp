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

class MainActivity : AppCompatActivity() {
    lateinit var mfusedlocation: FusedLocationProviderClient
    private var reqCode = 1010
    private lateinit var viewModel: MainViewModel
    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mfusedlocation = LocationServices.getFusedLocationProviderClient(this)
        getLastLocation()

        binding.tempText.setOnClickListener {
            getLastLocation()
        }

        binding.swipeToRefresh.setOnRefreshListener {
            getLastLocation()
            binding.swipeToRefresh.isRefreshing = false
        }

        val repository =Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        getLastLocation()
        var lat = mfusedlocation.lastLocation.result.latitude
        var lon = mfusedlocation.lastLocation.result.longitude
        viewModel.getWeatherData(APP_ID, lat, lon)
        viewModel.wResponse.observe(this, Observer {
            binding.tempText.text = it.body()?.main?.temp.toString()
        })
    }

    private fun getLastLocation() {
        if (checkPermission()) {
            if (locationEnable()) {
                mfusedlocation.lastLocation.addOnCompleteListener { task ->
                    var location: Location? = task.result
                    if (location == null) {
                        newLocation()
                    } else {
                        Log.i("Debz", location.longitude.toString() + " and " + location.latitude.toString())
                    }
                }
            } else {
                Toast.makeText(this, "Please Turn on GPS from settings", Toast.LENGTH_SHORT).show()
            }
        } else
            requestPermission()
    }

    @SuppressLint("MissingPermission")
    private fun newLocation() {
        var locationReq = com.google.android.gms.location.LocationRequest()
        locationReq.priority =
            com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
        locationReq.interval = 0
        locationReq.fastestInterval = 0
        locationReq.numUpdates = 1
        mfusedlocation = LocationServices.getFusedLocationProviderClient(this)
        mfusedlocation.requestLocationUpdates(locationReq, locationCallBack, Looper.myLooper())
    }

    private val locationCallBack = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            var lastLocation: Location = p0.lastLocation
        }
    }

    private fun locationEnable(): Boolean {
        var locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this, arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ), reqCode
        )
    }

    private fun checkPermission(): Boolean {
        if (
            ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == reqCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation()
            }
        }
    }
}