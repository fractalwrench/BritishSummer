package com.fractalwrench.britishsummer.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.fractalwrench.britishsummer.CurrentWeatherRepository
import com.fractalwrench.britishsummer.MainApplication
import com.fractalwrench.britishsummer.R
import com.fractalwrench.britishsummer.R.id.weather_results
import com.fractalwrench.britishsummer.WeatherApi
import com.jakewharton.rxbinding2.view.RxView.clicks
import io.reactivex.BackpressureStrategy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.main_fragment.location_button
import kotlinx.android.synthetic.main.main_fragment.city_field
import kotlinx.android.synthetic.main.main_fragment.weather_results
import javax.inject.Inject

class MainFragment : androidx.fragment.app.Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    @Inject
    lateinit var repository: CurrentWeatherRepository

    private lateinit var viewModel: CurrentWeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainApplication.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val subscribe = clicks(location_button)
                .map { city_field.text.toString() }
                .concatMap(repository::getCurrentWeather)
                .subscribe {
                    viewModel.weather.value = it
                }
        // TODO handle subscription cancellation
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CurrentWeatherViewModel::class.java)

        viewModel.weather.observe(this, Observer {
            weather_results.text = it?.name
        })
    }

}
