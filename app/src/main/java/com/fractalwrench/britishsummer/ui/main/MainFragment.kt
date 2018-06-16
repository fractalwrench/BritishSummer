package com.fractalwrench.britishsummer.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.fractalwrench.britishsummer.CurrentWeather
import com.fractalwrench.britishsummer.MainApplication
import com.fractalwrench.britishsummer.R
import com.fractalwrench.britishsummer.WeatherApi
import kotlinx.android.synthetic.main.main_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MainFragment : androidx.fragment.app.Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    @Inject
    lateinit var weatherApi: WeatherApi

    private lateinit var viewModel: MainViewModel

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

        // FIXME hacked together for testing
        location_button.setOnClickListener {
            val cityName = city_field.text.toString()
            val currentWeather = weatherApi.getCurrentWeather(cityName, "b6907d289e10d714a6e88b30761fae22")
            val response = currentWeather.enqueue(object : Callback<CurrentWeather> {
                override fun onFailure(call: Call<CurrentWeather>?, t: Throwable?) {
                    TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
                }

                override fun onResponse(call: Call<CurrentWeather>?, response: Response<CurrentWeather>?) {
                    TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
                }
            })
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }
}
