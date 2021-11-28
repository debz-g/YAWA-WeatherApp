package dev.redfox.yawa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.android.gms.location.FusedLocationProviderClient
import dev.redfox.yawa.databinding.ActivitySplashScreenBinding

private lateinit var binding: ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {
    lateinit var mfusedlocation:FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Handler().postDelayed(Runnable {

           startActivity(Intent(this,MainActivity::class.java))
            finish()
        },400)
    }
}