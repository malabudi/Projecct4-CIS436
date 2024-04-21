package com.weather.project4

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.weather.project4.databinding.FragmentLocationBinding
import java.util.Locale

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class LocationFragment : Fragment() {

    private var _binding: FragmentLocationBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel: WeatherViewModel
    private var weatherUpdatedListener: OnWeatherUpdatedListener? = null

    interface OnWeatherUpdatedListener {
        fun onWeatherUpdated(data: WeatherData)
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            weatherUpdatedListener = context as OnWeatherUpdatedListener
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString() + " must implement OnWeatherUpdatedListener")
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
        binding.progressBar.visibility = View.INVISIBLE

        binding.SearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val city = query?.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                } ?: ""
                if (city.isNotEmpty()) {
                    binding.cityLocationText.text = city  // Update cityLocationText with the query
                    viewModel.fetchWeather(city, binding.progressBar)
                }
                return true  // Return true to indicate that the query has been handled
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        viewModel.weatherData.observe(viewLifecycleOwner, Observer { weather ->
            // Instead of setting the text here, we'll notify the activity
            weatherUpdatedListener?.onWeatherUpdated(weather)
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}