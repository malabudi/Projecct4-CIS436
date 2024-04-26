package com.weather.project4

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.weather.project4.databinding.FragmentDataBinding
import java.util.Locale

class DataFragment : Fragment() {
    private var _binding: FragmentDataBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun updateTemperature(temperature: Double) {
        binding.temperature.text = "${temperature.toInt()}°F"
    }


    fun updateDescription(description: String) {
        val capitalizedDescription = description.split(" ")  // Split the string into words
            .map { word ->
                word.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                }
            }
            .joinToString(" ")  // Join the words back into a single string
        binding.description.text = capitalizedDescription
    }


    fun updateMinTemp(temp_Min: Double) {
        binding.MinTemp.text = " ${temp_Min.toInt()}°F"
    }

    fun updateMaxTemp(temp_Max: Double) {
        binding.MaxTemp.text = " ${temp_Max.toInt()}°F"

    }


    fun updateFeelsLike(feelsLike: Double) {
        binding.feelsLike.text = "Feels Like: ${feelsLike.toInt()}°F"
    }

    fun updateHumidity(humidity:Int){
        binding.humidtv.text = " ${humidity}%"
    }
}