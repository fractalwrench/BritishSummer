package com.fractalwrench.britishsummer

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.pressImeActionButton
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.runner.AndroidJUnit4
import com.fractalwrench.britishsummer.weather.Forecast
import com.fractalwrench.britishsummer.weather.forecast.ForecastViewModel
import com.fractalwrench.britishsummer.weather.forecast.ForecastFragment
import com.fractalwrench.britishsummer.weather.WeatherApi
import com.fractalwrench.britishsummer.weather.WeatherRepository
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers.single
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext.loadKoinModules
import org.koin.androidx.viewmodel.ext.koin.viewModel

@RunWith(AndroidJUnit4::class)
class ForecastFragmentTest : FragmentUiTest<Forecast>() {

    private lateinit var content: Forecast
    private val fragment = ForecastFragment.newInstance()
    private lateinit var viewModel: ForecastViewModel

    override val progressId = R.id.forecast_progress
    override val errorId = R.id.forecast_error
    override val placeholderId = R.id.forecast_placeholder

    @Before
    fun setUp() {
        content = JsonResourceReader().readJsonResource("/forecast.json", Forecast::class.java)
        addFragmentToSingleActivity(fragment)

        activityRule.activity.runOnUiThread {
            viewModel = fragment.viewModel
        }
    }

    override fun inject() {
        val weatherApi: WeatherApi = object : WeatherApi {
            override fun getWeatherForecast(cityName: String) = Observable.just(content)
            override fun getCurrentWeather(cityName: String) = TODO()
        }

        val testDoubleModule = module {
            viewModel { ForecastViewModel(get(), get("ui"), get()) }
            single { WeatherRepository(get()) }
            single { weatherApi }
        }

        loadKoinModules(listOf(testDoubleModule))
    }

    /**
     * Ensures that the view displays state changes when the viewmodel's live data is updated.
     */
    @Test
    fun showsContent() {
        updateUiState(UIState.Content(content))
        onView(withId(R.id.forecast_content))
            .check(matches(isDisplayed()))
    }

    override fun updateUiState(content: UIState<Forecast>) {
        activityRule.activity.runOnUiThread {
            viewModel.forecast.value = content
        }
    }

    /**
     * Ensure that the view reacts to user input by sending a message to the ViewModel.
     * This is verified with a fake repository
     */
    @Test
    fun requestsCityWeather() {
        onView(withId(R.id.city_field))
            .perform(typeText("some query"), closeSoftKeyboard())
            .perform(pressImeActionButton())

//        onView(withId(R.id.location_title))
//            .check(matches(withText("Zagreb - Centar")))
    }
}
