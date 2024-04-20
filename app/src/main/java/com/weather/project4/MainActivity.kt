package com.weather.project4

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.weather.project4.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), LocationFragment.OnWeatherUpdatedListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
    override fun onWeatherUpdated(data: WeatherData) {
        val dataFragment = supportFragmentManager.findFragmentById(R.id.dataFragment) as? DataFragment
        Log.i("Weather API Weatherdata", data.toString())

        dataFragment?.updateTemperature(data.temperature)
        dataFragment?.updateDescription(data.description)
        dataFragment?.updateMinTemp(data.tempMin)
        dataFragment?.updateMaxTemp(data.tempMax)
        dataFragment?.updateFeelsLike(data.feelsLike)

        // Update UI components or notify other fragments
        // Example: updating a detailed weather fragment or additional UI elements
        // This method will be called when weather data is updated from LocationFragment
    }
}