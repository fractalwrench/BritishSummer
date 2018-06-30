package com.fractalwrench.britishsummer.weather.forecast

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fractalwrench.britishsummer.weather.WeatherRepository
import com.fractalwrench.britishsummer.UIState
import com.fractalwrench.britishsummer.log.LogSeverity
import com.fractalwrench.britishsummer.log.Logger
import com.fractalwrench.britishsummer.weather.Forecast
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable

class ForecastViewModel(
    private val repository: WeatherRepository,
    private val scheduler: Scheduler,
    private val logger: Logger
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    val forecast: MutableLiveData<UIState<Forecast>> = MutableLiveData()

    fun init() {
        forecast.value = UIState.Placeholder()
    }

    fun showCity(cityName: String) {
        forecast.value = UIState.Progress()

        compositeDisposable.add(
            repository.getWeatherForecast(cityName)
                .observeOn(scheduler)
                .subscribe(
                    { forecast.value = UIState.Content(it) },
                    {
                        logger.log("Forecast request failed", it, LogSeverity.ERROR)
                        forecast.value = UIState.Error()
                    }
                )
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
