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
import com.fractalwrench.britishsummer.ui.main.CurrentWeatherViewModel
import com.fractalwrench.britishsummer.ui.main.MainFragment
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.architecture.ext.koin.viewModel
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext.loadKoinModules

@RunWith(AndroidJUnit4::class)
class WeatherUiTest {

    @Rule
    @JvmField
    val activityRule = object : ActivityTestRule<SingleFragmentActivity>(SingleFragmentActivity::class.java, true, true) {
        override fun beforeActivityLaunched() {
            super.beforeActivityLaunched()

            // TODO abstract all this DI

            val testDoubleModule = module {
                viewModel { CurrentWeatherViewModel(get()) }
                single { CurrentWeatherRepository(get()) }
                single {
                    val weatherApi: WeatherApi = object : WeatherApi {
                        override fun getWeatherForecast(cityName: String): Observable<Forecast> {
                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }

                        override fun getCurrentWeather(cityName: String): Observable<CurrentWeather> {
                            return Observable.just(weatherData)
                        }
                    }
                    weatherApi
                }
                single { Logger(arrayOf()) }
            }
            loadKoinModules(listOf(testDoubleModule))
        }
    }

    private lateinit var weatherData: CurrentWeather
    private val fragment = MainFragment()
    private lateinit var weatherModel: CurrentWeatherViewModel

    @Before
    fun setUp() {
        val activity = activityRule.activity
        weatherData = loadCurrentWeather()

        activity.runOnUiThread {
            activity.setFragment(fragment)
            activity.supportFragmentManager.executePendingTransactions()
            weatherModel = fragment.weatherModel
        }
    }

    // Test type 1: ensure that the view displays state changes correctly when the viewmodel
    // is updated

    @Test
    fun showsContent() {
        activityRule.activity.runOnUiThread {
            weatherModel.weather.value = UIState.Content(weatherData)
        }

        onView(withId(R.id.location_title))
                .check(matches(withText(weatherData.name)))

        // TODO add checks for other fields and other states
    }

    // Test type 2: ensure that the view reacts to user input by sending a message to the ViewModel.
    // This is verified with a fake repository

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

    fun loadCurrentWeather(): CurrentWeather {
        return JsonResourceReader().readJsonResource("/current_weather.json", CurrentWeather::class.java)
    }
}