package com.fractalwrench.britishsummer.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.fractalwrench.britishsummer.CurrentWeatherRepository
import com.fractalwrench.britishsummer.MainApplication
import com.fractalwrench.britishsummer.R
import com.fractalwrench.britishsummer.hideKeyboard
import com.jakewharton.rxbinding2.widget.RxTextView
import kotlinx.android.synthetic.main.main_fragment.city_field
import kotlinx.android.synthetic.main.main_fragment.humidity_desc
import kotlinx.android.synthetic.main.main_fragment.location_title
import kotlinx.android.synthetic.main.main_fragment.solar_desc
import kotlinx.android.synthetic.main.main_fragment.temp_desc
import kotlinx.android.synthetic.main.main_fragment.weather_desc
import kotlinx.android.synthetic.main.main_fragment.wind_desc
import java.util.Date
import javax.inject.Inject

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    @Inject
    lateinit var repository: CurrentWeatherRepository

    private lateinit var viewModel: CurrentWeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainApplication.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        // TODO add to disposable

        RxTextView.editorActions(city_field, {
            val isDone = it == EditorInfo.IME_ACTION_DONE
            if (isDone) {
                val cityName = city_field.text.toString()
                viewModel.showCity(repository, cityName) // FIXME move repository into viewmodel
                context?.hideKeyboard(city_field)
            }
            isDone
        }).subscribe()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CurrentWeatherViewModel::class.java)

        viewModel.weather.observe(this, Observer {
            // TODO return a sealed class of Data, Progress, Error?

            it!! // FIXME

            location_title.text = it.name
            weather_desc.text = it.weather[0].description // fixme check length, resource placeholders
            temp_desc.text = "Current: ${it.main.temp}, Min: ${it.main.temp_min}, Max: ${it.main.temp_max}"
            solar_desc.text = "Sunrise: ${Date(it.sys.sunrise)}, Sunset: ${Date(it.sys.sunset)}"
            wind_desc.text = "Wind speed: ${it.wind.speed}, Direction: ${it.wind.deg}"
            humidity_desc.text = "Humidity: ${it.main.humidity}%"
        })
    }
}
