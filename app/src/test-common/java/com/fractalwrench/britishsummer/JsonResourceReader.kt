package com.fractalwrench.britishsummer

internal class JsonResourceReader {

    fun <T> readJsonResource(name: String, clz: Class<*>): T {
        val moshi = moshi()
        val adapter = moshi.adapter<T>(clz)
        val json = javaClass.getResource(name).readText()
        return adapter.fromJson(json)!!
    }
}