package com.fractalwrench.britishsummer

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.Date
import javax.inject.Singleton

@Module
class NetworkModule(private val baseUrl: String, private val weatherApiKey: String) {

    @Provides
    fun moshi(): Moshi {
        return Moshi.Builder()
                .add(Date::class.java, Rfc3339DateJsonAdapter())
                .build()
    }

    @Provides
    fun converterFactory(moshi: Moshi): Converter.Factory {
        return MoshiConverterFactory.create(moshi)
    }

    @Provides
    fun interceptor(): Interceptor { // TODO test me
        return Interceptor {
            val request = it.request()
            val adaptedUrl = request.url().newBuilder()
                    .addQueryParameter("appid", weatherApiKey)
                    .build()

            val adaptedRequest = request.newBuilder().url(adaptedUrl).build()
            it.proceed(adaptedRequest)
        }
    }

    @Provides
    @Singleton
    fun cache(context: Context): Cache {
        return Cache(context.cacheDir, 16 * 1024 * 1024)
    }

    @Provides
    @Singleton
    fun okHttpClient(interceptor: Interceptor, cache: Cache): OkHttpClient {
        return OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(interceptor)
                .build()
    }

    @Provides
    @Singleton
    fun retrofit(httpClient: OkHttpClient, converterFactory: Converter.Factory): Retrofit {
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(httpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(converterFactory)
                .build()
    }

    @Provides
    @Singleton
    fun weatherApi(retrofit: Retrofit): WeatherApi {
        return retrofit.create(WeatherApi::class.java)
    }
}