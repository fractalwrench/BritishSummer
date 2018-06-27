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
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
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
import java.util.concurrent.TimeUnit

/**
 * Scheduler that instantly executes the task.
 */
class InstantScheduler : Scheduler() {
    override fun createWorker(): Worker =
            object : Worker() {
                override fun isDisposed(): Boolean = false

                override fun dispose() = Unit

                override fun schedule(run: Runnable, delay: Long, unit: TimeUnit): Disposable {
                    run.run()
                    return Disposables.empty()
                }
            }
}

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    internal val weatherModel: CurrentWeatherViewModel by viewModel()
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
        weatherModel.weather.nonNullObserve(this, this::bindWeatherUiState)
    }

    internal fun bindWeatherUiState(it: UIState<CurrentWeather>?) {
        when (it) {
            is UIState.Error -> TODO()
            is UIState.Progress -> TODO()
            is UIState.Placeholder -> TODO()
            is UIState.Content -> showViewData(it.data)
        }
    }

    private fun showViewData(weather: CurrentWeather) {
        location_title.text = weather.name
        weather_desc.text = weather.weather[0].description // fixme check length
        temp_desc.text = "Current: ${weather.main.temp}, Min: ${weather.main.temp_min}, Max: ${weather.main.temp_max}"
        solar_desc.text = "Sunrise: ${Date(weather.sys.sunrise)}, Sunset: ${Date(weather.sys.sunset)}"
        wind_desc.text = "Wind speed: ${weather.wind.speed}, Direction: ${weather.wind.deg}"
        humidity_desc.text = "Humidity: ${weather.main.humidity}%"
    }
}
