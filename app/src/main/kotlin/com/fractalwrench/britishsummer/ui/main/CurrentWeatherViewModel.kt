package com.fractalwrench.britishsummer.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fractalwrench.britishsummer.CurrentWeather
import com.fractalwrench.britishsummer.CurrentWeatherRepository
import com.fractalwrench.britishsummer.UIState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class CurrentWeatherViewModel(private val repository: CurrentWeatherRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    val weather: MutableLiveData<UIState<CurrentWeather>> = MutableLiveData()

    fun showCity(cityName: String) {
        compositeDisposable.add(
                repository.getCurrentWeather(cityName)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { weather.value = UIState.Content(it) }
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
