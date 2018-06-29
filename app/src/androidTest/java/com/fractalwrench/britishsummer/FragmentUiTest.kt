package com.fractalwrench.britishsummer

import androidx.fragment.app.Fragment
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.junit.Rule

import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
abstract class FragmentUiTest {

    @Rule
    @JvmField
    val activityRule = object : ActivityTestRule<SingleFragmentActivity>(SingleFragmentActivity::class.java, true, true) {
        override fun beforeActivityLaunched() {
            super.beforeActivityLaunched()
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
