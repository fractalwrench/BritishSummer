package com.fractalwrench.britishsummer

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fractalwrench.britishsummer.ui.main.MainFragment
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MainApplication.appComponent.inject(this)

        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }
    }
}
