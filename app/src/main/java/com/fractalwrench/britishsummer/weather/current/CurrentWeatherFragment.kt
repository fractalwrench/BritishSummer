package com.fractalwrench.britishsummer.weather.current

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import com.fractalwrench.britishsummer.BaseFragment
import com.fractalwrench.britishsummer.weather.CurrentWeather
import com.fractalwrench.britishsummer.R
import com.fractalwrench.britishsummer.UIState
import com.fractalwrench.britishsummer.convertFromUnixToJavaEpoch
import com.fractalwrench.britishsummer.hideKeyboard
import com.fractalwrench.britishsummer.nonNullObserve
import com.fractalwrench.britishsummer.weather.forecast.ForecastFragment
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Scheduler
import kotlinx.android.synthetic.main.current_weather_fragment.city_field
import kotlinx.android.synthetic.main.current_weather_fragment.humidity_desc
import kotlinx.android.synthetic.main.current_weather_fragment.location_title
import kotlinx.android.synthetic.main.current_weather_fragment.solar_desc
import kotlinx.android.synthetic.main.current_weather_fragment.temp_desc
import kotlinx.android.synthetic.main.current_weather_fragment.weather_desc
import kotlinx.android.synthetic.main.current_weather_fragment.wind_desc
import org.koin.android.architecture.ext.android.viewModel
import org.koin.android.ext.android.inject
import java.util.Date
import java.util.concurrent.TimeUnit

class CurrentWeatherFragment : BaseFragment() {

    companion object {
        fun newInstance() = CurrentWeatherFragment()
    }

    internal val scheduler: Scheduler by inject("ui")
    internal val viewModel: CurrentWeatherViewModel by viewModel()
    override val layoutId: Int = R.layout.current_weather_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        compositeDisposable?.add(
            RxTextView.editorActions(city_field)
                .filter { it == EditorInfo.IME_ACTION_DONE }
                .debounce(1000, TimeUnit.MILLISECONDS, scheduler)
                .forEach { handleNewLocation() }
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.weather.nonNullObserve(this, this::bindWeatherUiState)
    }

    private fun handleNewLocation() {
        logger.log("Finding weather for city")
        val cityName = city_field.text.toString()
        viewModel.showCity(cityName)
        context?.hideKeyboard(city_field)
    }

    private fun bindWeatherUiState(it: UIState<CurrentWeather>?) {
        when (it) {
            is UIState.Error -> TODO()
            is UIState.Progress -> TODO()
            is UIState.Placeholder -> TODO()
            is UIState.Content -> showViewData(it.data)
        }
    }

    private fun showViewData(weather: CurrentWeather) {
        location_title.text = weather.name
        weather_desc.text = weather.weather?.get(0)?.description // fixme check length
        temp_desc.text = "Current: ${weather.main?.temp}, Min: ${weather.main?.temp_min}, Max: ${weather.main?.temp_max}"
        solar_desc.text = "Sunrise: ${Date(weather.sys?.sunrise!!.convertFromUnixToJavaEpoch())}, Sunset: ${Date(weather.sys.sunset!!.convertFromUnixToJavaEpoch())}"
        wind_desc.text = "Wind speed: ${weather.wind?.speed}, Direction: ${weather.wind?.deg}"
        humidity_desc.text = "Humidity: ${weather.main?.humidity}%"
    }
}
