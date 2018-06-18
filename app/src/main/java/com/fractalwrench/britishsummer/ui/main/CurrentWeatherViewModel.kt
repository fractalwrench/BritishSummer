package com.fractalwrench.britishsummer.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fractalwrench.britishsummer.CurrentWeather

class CurrentWeatherViewModel : ViewModel() {

    val weather: MutableLiveData<CurrentWeather> = MutableLiveData()

}
