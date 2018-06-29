package com.fractalwrench.britishsummer.weather.current

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fractalwrench.britishsummer.weather.CurrentWeather
import com.fractalwrench.britishsummer.weather.WeatherRepository
import com.fractalwrench.britishsummer.UIState
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class CurrentWeatherViewModel(
    private val repository: WeatherRepository,
    private val scheduler: Scheduler
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    val weather: MutableLiveData<UIState<CurrentWeather>> = MutableLiveData()

    fun showCity(cityName: String) {
        compositeDisposable.add(
            repository.getCurrentWeather(cityName)
                .observeOn(scheduler)
                .subscribe { weather.value = UIState.Content(it) }
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
