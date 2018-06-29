package com.fractalwrench.britishsummer

import androidx.fragment.app.Fragment
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.fractalwrench.britishsummer.log.Logger
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import org.junit.Rule

import org.junit.runner.RunWith
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext.loadKoinModules

@RunWith(AndroidJUnit4::class)
abstract class FragmentUiTest {

    @Rule
    @JvmField
    val activityRule = object : ActivityTestRule<SingleFragmentActivity>(SingleFragmentActivity::class.java, true, true) {
        override fun beforeActivityLaunched() {
            super.beforeActivityLaunched()

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

}
