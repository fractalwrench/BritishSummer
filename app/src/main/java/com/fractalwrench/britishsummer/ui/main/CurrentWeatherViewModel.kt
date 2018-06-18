package com.fractalwrench.britishsummer.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CurrentWeatherViewModel : ViewModel() {

    val cityName: MutableLiveData<String> = MutableLiveData()

}
