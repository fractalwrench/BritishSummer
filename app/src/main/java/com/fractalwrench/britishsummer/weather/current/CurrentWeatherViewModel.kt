package com.fractalwrench.britishsummer.weather.current

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fractalwrench.britishsummer.response.CurrentWeather
import com.fractalwrench.britishsummer.weather.WeatherRepository
import com.fractalwrench.britishsummer.UIState
import com.fractalwrench.britishsummer.log.LogSeverity
import com.fractalwrench.britishsummer.log.Logger
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable

class CurrentWeatherViewModel(
    private val repository: WeatherRepository,
    private val scheduler: Scheduler,
    private val logger: Logger
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    val weather: MutableLiveData<UIState<CurrentWeather>> = MutableLiveData()

    fun init() {
        weather.value = UIState.Placeholder()
    }

    fun showCity(cityName: String) {
        weather.value = UIState.Progress()

        compositeDisposable.add(
            repository.getCurrentWeather(cityName)
                .observeOn(scheduler)
                .subscribe({ weather.value = UIState.Content(it) },
                    {
                        logger.log("Current weather request failed", it, LogSeverity.ERROR)
                        weather.value = UIState.Error()
                    }
                )
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
