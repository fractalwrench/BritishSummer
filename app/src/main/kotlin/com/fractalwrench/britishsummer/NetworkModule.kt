package com.fractalwrench.britishsummer

import android.content.Context
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

fun generateNetworkModule(baseUrl: String, weatherApiKey: String): Module {
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

fun moshi(): Moshi {
    return Moshi.Builder()
            .add(Date::class.java, Rfc3339DateJsonAdapter())
            .build()
}

fun converterFactory(moshi: Moshi): Converter.Factory {
    return MoshiConverterFactory.create(moshi)
}

fun interceptor(weatherApiKey: String): Interceptor { // TODO test me
    return Interceptor {
        val request = it.request()
        val adaptedUrl = request.url().newBuilder()
                .addQueryParameter("appid", weatherApiKey)
                .build()

        val adaptedRequest = request.newBuilder().url(adaptedUrl).build()
        it.proceed(adaptedRequest)
    }
}

fun cache(context: Context): Cache {
    return Cache(context.cacheDir, 16 * 1024 * 1024)
}

fun okHttpClient(interceptor: Interceptor, cache: Cache): OkHttpClient {
    return OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(interceptor)
            .build()
}

fun retrofit(httpClient: OkHttpClient, converterFactory: Converter.Factory, baseUrl: String): Retrofit {
    return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(converterFactory)
            .build()
}

fun weatherApi(retrofit: Retrofit): WeatherApi {
    return retrofit.create(WeatherApi::class.java)
}
