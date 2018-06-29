package com.fractalwrench.britishsummer.weather.forecast

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fractalwrench.britishsummer.weather.CurrentWeather
import com.fractalwrench.britishsummer.weather.WeatherRepository
import com.fractalwrench.britishsummer.UIState
import com.fractalwrench.britishsummer.weather.Forecast
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class ForecastViewModel(
    private val repository: WeatherRepository,
    private val scheduler: Scheduler
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    val forecast: MutableLiveData<UIState<Forecast>> = MutableLiveData()

    fun showCity(cityName: String) {
        compositeDisposable.add(
            repository.getWeatherForecast(cityName)
                .observeOn(scheduler)
                .subscribe({
                    forecast.value = UIState.Content(it)
                },
                    {
                        Timber.e(it)
                    })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
