package com.fractalwrench.britishsummer

import androidx.fragment.app.Fragment
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.fractalwrench.britishsummer.log.Logger
import com.fractalwrench.britishsummer.weather.Forecast
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import org.junit.Rule
import org.junit.Test

import org.junit.runner.RunWith
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext.closeKoin
import org.koin.standalone.StandAloneContext.loadKoinModules

@RunWith(AndroidJUnit4::class)
abstract class FragmentUiTest<T> {

    protected abstract val progressId: Int
    protected abstract val errorId: Int
    protected abstract val placeholderId: Int

    @Rule
    @JvmField
    val activityRule = object : ActivityTestRule<SingleFragmentActivity>(SingleFragmentActivity::class.java, true, true) {
        override fun beforeActivityLaunched() {
            super.beforeActivityLaunched()

            // close any previous koin contexts
            closeKoin()

            val testDoubles = module {
                // schedulers
                single("ui", { InstantScheduler as Scheduler })
                single("io", { Schedulers.io() })
                single("compute", { Schedulers.computation() })

                // logger
                single { Logger(arrayOf()) }
            }

            loadKoinModules(listOf(testDoubles))

            // inject any other values required

            inject()
        }
    }

    /**
     * Injects a koin graph before a harness activity is launched, overriding production
     * dependencies with test doubles
     */
    protected abstract fun inject()

    /**
     * Adds a fragment to a harness activity for test purposes
     */
    protected fun addFragmentToSingleActivity(fragment: Fragment) {
        val activity = activityRule.activity
        activity.runOnUiThread {
            activity.setFragment(fragment)
            activity.supportFragmentManager.executePendingTransactions()
        }
    }

    @Test
    fun showsError() {
        updateUiState(UIState.Error())
        Espresso.onView(ViewMatchers.withId(errorId))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun showsPlaceholder() {
        updateUiState(UIState.Placeholder())
        Espresso.onView(ViewMatchers.withId(placeholderId))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun showsProgress() {
        updateUiState(UIState.Progress())
        Espresso.onView(ViewMatchers.withId(progressId))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    protected abstract fun updateUiState(content: UIState<T>)

}
