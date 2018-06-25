package com.fractalwrench.britishsummer.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import com.fractalwrench.britishsummer.CurrentWeather
import com.fractalwrench.britishsummer.R
import com.fractalwrench.britishsummer.UIState
import com.fractalwrench.britishsummer.debounceUi
import com.fractalwrench.britishsummer.hideKeyboard
import com.fractalwrench.britishsummer.log.Logger
import com.fractalwrench.britishsummer.nonNullObserve
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.main_fragment.city_field
import kotlinx.android.synthetic.main.main_fragment.humidity_desc
import kotlinx.android.synthetic.main.main_fragment.location_title
import kotlinx.android.synthetic.main.main_fragment.solar_desc
import kotlinx.android.synthetic.main.main_fragment.temp_desc
import kotlinx.android.synthetic.main.main_fragment.weather_desc
import kotlinx.android.synthetic.main.main_fragment.wind_desc
import org.koin.android.architecture.ext.android.viewModel
import org.koin.android.ext.android.inject
import java.util.Date

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val weatherModel: CurrentWeatherViewModel by viewModel()
    private val logger: Logger by inject()
    private var compositeDisposable: CompositeDisposable? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        compositeDisposable = CompositeDisposable()

        compositeDisposable?.add(
            RxTextView.editorActions(city_field)
                    .filter { it == EditorInfo.IME_ACTION_DONE }
                    .debounceUi()
                    .forEach {
                        logger.log("Finding weather for city")
                        val cityName = city_field.text.toString()
                        weatherModel.showCity(cityName)
                        context?.hideKeyboard(city_field)
                    }
        )
    }

    override fun onDestroyView() {
        compositeDisposable?.dispose()
        super.onDestroyView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        weatherModel.weather.nonNullObserve(this, {
            when (it) {
                is UIState.Error -> TODO()
                is UIState.Progress -> TODO()
                is UIState.Placeholder -> TODO()
                is UIState.Content -> showViewData(it.data)
            }
        })
    }

    fun showViewData(weather: CurrentWeather) {
        location_title.text = weather.name
        weather_desc.text = weather.weather[0].description // fixme check length
        temp_desc.text = "Current: ${weather.main.temp}, Min: ${weather.main.temp_min}, Max: ${weather.main.temp_max}"
        solar_desc.text = "Sunrise: ${Date(weather.sys.sunrise)}, Sunset: ${Date(weather.sys.sunset)}"
        wind_desc.text = "Wind speed: ${weather.wind.speed}, Direction: ${weather.wind.deg}"
        humidity_desc.text = "Humidity: ${weather.main.humidity}%"
    }
}
