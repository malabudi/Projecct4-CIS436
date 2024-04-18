package com.weather.project4

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.AndroidViewModel
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject


class WeatherViewModel(application: Application) : AndroidViewModel(application)  {
    private val apiKey: String = "064c9b7b08339d54380e867740704f94"

    public fun fetchWeather(progressBar: ProgressBar) {
        progressBar.visibility = View.VISIBLE

        // Used to add a two second delay to the request to show the progress bar
        val handler = Handler(Looper.getMainLooper())

        handler.postDelayed({
            val requestQueue =
                Volley.newRequestQueue(getApplication<Application>().applicationContext)
            val requestUrl =
                "https://api.openweathermap.org/data/2.5/weather?lat=42.32&lon=-83.18&appid=${apiKey}&units=imperial"

            val request = StringRequest(Request.Method.GET, requestUrl,
                { response ->
                    try {
                        val resBody = JSONObject(response)
                        Log.i("Weather API Response", resBody.toString())

                        progressBar.visibility = View.INVISIBLE
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                {
                    Log.e("MainViewModel", "Failed to fetch weather")
                }
            )

            requestQueue.add(request)
        }, 400)
    }
}