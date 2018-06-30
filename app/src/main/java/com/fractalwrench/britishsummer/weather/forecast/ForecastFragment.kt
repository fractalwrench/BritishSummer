package com.fractalwrench.britishsummer.weather.forecast

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.LinearLayoutManager
import com.fractalwrench.britishsummer.BaseFragment
import com.fractalwrench.britishsummer.R
import com.fractalwrench.britishsummer.UIState
import com.fractalwrench.britishsummer.hideKeyboard
import com.fractalwrench.britishsummer.nonNullObserve
import com.fractalwrench.britishsummer.toViewPosition
import com.fractalwrench.britishsummer.weather.Forecast
import com.fractalwrench.britishsummer.weather.ViewPosition
import com.fractalwrench.britishsummer.weather.WeatherPrediction
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Scheduler
import kotlinx.android.synthetic.main.current_weather_fragment.city_field
import kotlinx.android.synthetic.main.current_weather_fragment.location_title
import kotlinx.android.synthetic.main.forecast_fragment.forecast_content
import kotlinx.android.synthetic.main.forecast_fragment.forecast_view_flipper
import org.koin.android.architecture.ext.android.viewModel
import org.koin.android.ext.android.inject
import java.util.concurrent.TimeUnit

class ForecastFragment : BaseFragment() {

    companion object {
        fun newInstance() = ForecastFragment()
    }

    internal val scheduler: Scheduler by inject("ui")
    internal val viewModel: ForecastViewModel by viewModel()
    private val forecastList = mutableListOf<WeatherPrediction>()

    private lateinit var weatherAdapter: ForecastAdapter

    override val layoutId: Int = R.layout.forecast_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        compositeDisposable?.add(
            RxTextView.editorActions(city_field)
                .filter { it == EditorInfo.IME_ACTION_DONE }
                .debounce(1000, TimeUnit.MILLISECONDS, scheduler)
                .forEach { handleNewLocation() }
        )

        weatherAdapter = ForecastAdapter(forecastList)

        forecast_content.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = weatherAdapter
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.forecast.nonNullObserve(this, this::bindWeatherUiState)
    }

    private fun handleNewLocation() {
        logger.log("Finding weather for city")
        val cityName = city_field.text.toString()
        viewModel.showCity(cityName)
        context?.hideKeyboard(city_field)
    }

    private fun bindWeatherUiState(it: UIState<Forecast>) {
        updateDisplayedView(it.toViewPosition())

        when (it) {
            is UIState.Content -> showViewData(it.data)
        }
    }

    private fun updateDisplayedView(position: ViewPosition) {
        forecast_view_flipper.displayedChild = position.pos
    }

    private fun showViewData(forecast: Forecast) {
        updateDisplayedView(ViewPosition.CONTENT)
        forecastList.clear()
        forecastList.addAll(forecast.list as Array<WeatherPrediction>)
        weatherAdapter.notifyDataSetChanged()
    }
}


