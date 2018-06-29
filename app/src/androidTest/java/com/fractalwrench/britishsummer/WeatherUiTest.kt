package com.fractalwrench.britishsummer

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.pressImeActionButton
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.fractalwrench.britishsummer.log.Logger
import com.fractalwrench.britishsummer.weather.CurrentWeather
import com.fractalwrench.britishsummer.weather.Forecast
import com.fractalwrench.britishsummer.weather.current.CurrentWeatherViewModel
import com.fractalwrench.britishsummer.weather.current.CurrentWeatherFragment
import com.fractalwrench.britishsummer.weather.WeatherApi
import com.fractalwrench.britishsummer.weather.WeatherRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.architecture.ext.koin.viewModel
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext.loadKoinModules

@RunWith(AndroidJUnit4::class)
class WeatherUiTest : FragmentUiTest() {

    private lateinit var weatherData: CurrentWeather
    private val fragment = CurrentWeatherFragment()
    private lateinit var weatherModel: CurrentWeatherViewModel

    @Before
    fun setUp() {
        weatherData = JsonResourceReader().readJsonResource("/current_weather.json", CurrentWeather::class.java)
        addFragmentToSingleActivity(fragment)

        activityRule.activity.runOnUiThread {
            weatherModel = fragment.weatherModel
        }
    }

    override fun inject() {
        val weatherApi: WeatherApi = object : WeatherApi {
            override fun getWeatherForecast(cityName: String) = TODO()
            override fun getCurrentWeather(cityName: String) = Observable.just(weatherData)
        }

        val testDoubleModule = module {
            viewModel { CurrentWeatherViewModel(get(), get("ui")) }
            single { WeatherRepository(get()) }
            single { weatherApi }
            single { Logger(arrayOf()) }
        }

        val testSchedulers = module {
            single("ui", { AndroidSchedulers.mainThread() })
            single("io", { Schedulers.io() })
            single("compute", { Schedulers.computation() })
        }
        loadKoinModules(listOf(testDoubleModule, testSchedulers))
    }

    /**
     * Ensures that the view displays state changes when the viewmodel's live data is updated.
     */
    @Test
    fun showsContent() {
        activityRule.activity.runOnUiThread {
            weatherModel.weather.value = UIState.Content(weatherData)
        }

        onView(withId(R.id.location_title))
            .check(matches(withText(weatherData.name)))

        // TODO add checks for other fields and other states
    }

    /**
     * Ensure that the view reacts to user input by sending a message to the ViewModel.
     * This is verified with a fake repository
     */
    @Test
    fun requestsCityWeather() {
        TimeScheduler.timeSchedulerHandler = { default, tag ->
            if (tag == "click_debounce") InstantScheduler else default
        }

        onView(withId(R.id.city_field))
            .perform(typeText("some query"), closeSoftKeyboard())
            .perform(pressImeActionButton())

        onView(withId(R.id.location_title))
            .check(matches(withText("London")))
    }

}
