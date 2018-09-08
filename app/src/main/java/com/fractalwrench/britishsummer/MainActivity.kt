package com.fractalwrench.britishsummer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fractalwrench.britishsummer.weather.forecast.ForecastFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, ForecastFragment.newInstance())
                    .commitNow()
        }
    }
}
