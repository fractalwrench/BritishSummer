package com.fractalwrench.britishsummer

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.pressImeActionButton
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.runner.AndroidJUnit4
import com.fractalwrench.britishsummer.response.CurrentWeather
import com.fractalwrench.britishsummer.weather.WeatherApi
import com.fractalwrench.britishsummer.weather.WeatherRepository
import com.fractalwrench.britishsummer.weather.current.CurrentWeatherFragment
import com.fractalwrench.britishsummer.weather.current.CurrentWeatherViewModel
import io.reactivex.Observable
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext.loadKoinModules

@RunWith(AndroidJUnit4::class)
class CurrentWeatherFragmentTest : FragmentUiTest<CurrentWeather>() {

    private lateinit var content: CurrentWeather
    private val fragment = CurrentWeatherFragment.newInstance()
    private lateinit var viewModel: CurrentWeatherViewModel

    override val progressId = R.id.weather_progress
    override val errorId = R.id.weather_error
    override val placeholderId = R.id.weather_placeholder

    @Before
    fun setUp() {
        content = JsonResourceReader().readJsonResource("/current_weather.json", CurrentWeather::class.java)
        addFragmentToSingleActivity(fragment)

        activityRule.activity.runOnUiThread {
            viewModel = fragment.viewModel
        }
    }

    override fun inject() {
        val weatherApi: WeatherApi = object : WeatherApi {
            override fun getWeatherForecast(cityName: String) = TODO()
            override fun getCurrentWeather(cityName: String) = Observable.just(content)
        }

        val testDoubleModule = module {
            viewModel { CurrentWeatherViewModel(get(), get("ui"), get()) }
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

        onView(withId(R.id.weather_content))
            .check(matches(ViewMatchers.isDisplayed()))

        onView(withId(R.id.location_title))
            .check(matches(withText(content.name)))
    }

    override fun updateUiState(content: UIState<CurrentWeather>) {
        activityRule.activity.runOnUiThread {
            viewModel.weather.value = content
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

        onView(withId(R.id.location_title))
            .check(matches(withText("London")))
    }
}
