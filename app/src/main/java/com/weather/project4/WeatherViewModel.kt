package com.weather.project4

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

data class WeatherData(
    val temperature: Double,
    val feelsLike: Double,
    val tempMin: Double,
    val tempMax: Double,
    val pressure: Int,
    val humidity: Int,
    val description: String,
    val icon: String
)

class WeatherViewModel(application: Application) : AndroidViewModel(application)  {
    private val API_KEY: String = "1faa0881d765dafd27f4814945c3a427"
    private var CITY ="Detroit"
    private val _weatherData = MutableLiveData<WeatherData>()
    val weatherData: LiveData<WeatherData> = _weatherData
    fun fetchWeather(progressBar: ProgressBar) {
        progressBar.visibility = View.VISIBLE
        // Used to add a two second delay to the request to show the progress bar
        val handler = Handler(Looper.getMainLooper())

        handler.postDelayed({
            val requestQueue =
                Volley.newRequestQueue(getApplication<Application>().applicationContext)
            val requestUrl =
                "https://api.openweathermap.org/data/2.5/weather?q=$CITY&appid=$API_KEY"

            val request = StringRequest(Request.Method.GET, requestUrl,
                { response ->
                    try {
                        val resBody = JSONObject(response)
                        val main = resBody.getJSONObject("main")
                        val weatherArray = resBody.getJSONArray("weather")
                        val weather = weatherArray.getJSONObject(0)

                        val weatherData = WeatherData(
                            temperature = main.getDouble("temp"),
                            feelsLike = main.getDouble("feels_like"),
                            tempMin = main.getDouble("temp_min"),
                            tempMax = main.getDouble("temp_max"),
                            pressure = main.getInt("pressure"),
                            humidity = main.getInt("humidity"),
                            description = weather.getString("description"),
                            icon = weather.getString("icon")
                        )
                        // Update the LiveData with the fetched weather data
                        _weatherData.postValue(weatherData)
                        progressBar.visibility = View.INVISIBLE
                        Log.i("Weather API Response", resBody.toString())
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                {
                    Log.e("MainViewModel", "Failed to fetch weather")
                    progressBar.visibility = View.INVISIBLE
                }
            )

            requestQueue.add(request)
        }, 400)
    }
}