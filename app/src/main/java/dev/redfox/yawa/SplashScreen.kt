package dev.redfox.yawa

import android.content.Intent
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dev.redfox.yawa.databinding.ActivitySplashScreenBinding

private lateinit var binding: ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {
    lateinit var mfusedlocation: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mfusedlocation = LocationServices.getFusedLocationProviderClient(this)
        getLastLocation()

        Handler().postDelayed(Runnable {

            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 400)
    }

    private fun getLastLocation() {
        if (checkPermission()) {
            if(locationEnable()){
                mfusedlocation.lastLocation.addOnCompleteListener {
                    task->
                    var location:Location?=task.result
                }
            }
        }
        else
            requestPermission()
    }

    private fun locationEnable(): Boolean {

    }

    private fun requestPermission() {

    }

    private fun checkPermission(): Boolean{

    }
}