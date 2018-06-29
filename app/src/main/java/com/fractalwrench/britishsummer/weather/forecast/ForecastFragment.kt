package com.fractalwrench.britishsummer.weather.forecast

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fractalwrench.britishsummer.BaseFragment
import com.fractalwrench.britishsummer.R
import com.fractalwrench.britishsummer.UIState
import com.fractalwrench.britishsummer.hideKeyboard
import com.fractalwrench.britishsummer.nonNullObserve
import com.fractalwrench.britishsummer.weather.Forecast
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Scheduler
import kotlinx.android.synthetic.main.current_weather_fragment.city_field
import kotlinx.android.synthetic.main.current_weather_fragment.location_title
import kotlinx.android.synthetic.main.forecast_fragment.recycler_view
import org.koin.android.architecture.ext.android.viewModel
import org.koin.android.ext.android.inject
import java.util.concurrent.TimeUnit

class ForecastFragment : BaseFragment() {

    companion object {
        fun newInstance() = ForecastFragment()
    }

    internal val scheduler: Scheduler by inject("ui")
    internal val viewModel: ForecastViewModel by viewModel()

    private lateinit var weatherAdapter: WeatherForecastAdapter

    override val layoutId: Int = R.layout.forecast_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        compositeDisposable?.add(
            RxTextView.editorActions(city_field)
                .filter { it == EditorInfo.IME_ACTION_DONE }
                .debounce(1000, TimeUnit.MILLISECONDS, scheduler)
                .forEach { handleNewLocation() }
        )

        weatherAdapter = WeatherForecastAdapter(arrayOf("foo", "bar", "whoops"))

        recycler_view.apply {
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

    private fun bindWeatherUiState(it: UIState<Forecast>?) {
        when (it) {
            is UIState.Error -> TODO()
            is UIState.Progress -> TODO()
            is UIState.Placeholder -> TODO()
            is UIState.Content -> showViewData(it.data)
        }
    }

    private fun showViewData(forecast: Forecast) {
        location_title.text = forecast.city?.name
    }
}


