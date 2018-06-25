package com.fractalwrench.britishsummer

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.fractalwrench.britishsummer.ui.main.MainFragment
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WeatherUiTest {

    // TODO adapt more to the following https://github.com/googlesamples/android-architecture-components/blob/master/GithubBrowserSample/app/src/androidTest/java/com/android/example/github/ui/user/UserFragmentTest.kt

    // Want to:
    //
    // Submit the text field, and assert that the state returned by the mocked viewmodel
    // is displayed by the view correctly
    //
    // This tests the binding AND that the viewmodel calls the correct method.

    @Rule
    @JvmField
    val activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    lateinit var fragment: MainFragment

    @Before
    fun setUp() {
        val fragmentManager = activityRule.activity.supportFragmentManager
        fragment = fragmentManager.findFragmentById(R.id.container) as MainFragment
    }

    // tests that content displays correctly

    @Test
    fun showsContent() {
        val data = loadCurrentWeather()

        activityRule.activity.runOnUiThread {
            fragment.bindWeatherUiState(UIState.Content(data))
        }

        onView(withId(R.id.location_title))
                .check(matches(withText(data.name)))

        // TODO add checks for other fields and other states
    }

    // FIXME DRY
    private val moshi = moshi()
    private val adapter = moshi.adapter<CurrentWeather>(CurrentWeather::class.java)

    fun loadCurrentWeather(): CurrentWeather {
        val json = javaClass.getResource("/current_weather.json").readText()
        return adapter.fromJson(json)!!
    }
}
