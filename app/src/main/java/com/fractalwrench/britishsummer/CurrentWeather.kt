package com.fractalwrench.britishsummer

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class CurrentWeather(val name: String)
