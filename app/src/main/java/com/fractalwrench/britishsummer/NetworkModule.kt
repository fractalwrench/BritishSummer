package com.fractalwrench.britishsummer

import android.content.Context
import com.fractalwrench.britishsummer.weather.WeatherApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.dsl.module.Module
import org.koin.dsl.module.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.Date

internal fun generateNetworkModule(baseUrl: String, weatherApiKey: String): Module {
    return module {
        single { retrofit(get(), get(), baseUrl) }
        single { cache(get()) }
        single { weatherApi(get()) }
        factory { moshi() }
        factory { converterFactory(get()) }
        factory { interceptor(weatherApiKey) }
        factory { okHttpClient(get(), get()) }
    }
}

internal fun moshi(): Moshi {
    return Moshi.Builder()
            .add(Date::class.java, Rfc3339DateJsonAdapter())
            .build()
}

internal fun interceptor(weatherApiKey: String): Interceptor {
    return Interceptor {
        val request = it.request()
        val adaptedUrl = request.url().newBuilder()
            .addQueryParameter("appid", weatherApiKey)
            .addQueryParameter("units", "metric")
            .build()

        val adaptedRequest = request.newBuilder().url(adaptedUrl).build()
        it.proceed(adaptedRequest)
    }
}

private fun converterFactory(moshi: Moshi): Converter.Factory {
    return MoshiConverterFactory.create(moshi)
}

private fun cache(context: Context): Cache {
    return Cache(context.cacheDir, 16 * 1024 * 1024)
}

private fun okHttpClient(interceptor: Interceptor, cache: Cache): OkHttpClient {
    return OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(interceptor)
            .build()
}

private fun retrofit(httpClient: OkHttpClient, converterFactory: Converter.Factory, baseUrl: String): Retrofit {
    return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(converterFactory)
            .build()
}

private fun weatherApi(retrofit: Retrofit): WeatherApi {
    return retrofit.create(WeatherApi::class.java)
}
