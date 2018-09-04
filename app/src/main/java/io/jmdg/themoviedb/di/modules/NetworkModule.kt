package io.jmdg.themoviedb.di.modules

import dagger.Module
import dagger.Provides
import io.jmdg.themoviedb.BuildConfig
import io.jmdg.themoviedb.data.remote.api.MovieApi
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Joshua de Guzman on 04/09/2018.
 */

@Module
class NetworkModule {
    private val URL = "https://api.themoviedb.org/3/"
    private val API_KEY = "973e0b034af17e62d03ca343795ac965"

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
        val clientBuilder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            clientBuilder.addInterceptor(httpLoggingInterceptor)
        }
        clientBuilder.addInterceptor {
            val originalRequest = it.request()
            val url = originalRequest.url()
                    .newBuilder()
                    .addQueryParameter("api_key", API_KEY)
                    .build()
            val requestBuilder = originalRequest.newBuilder().url(url).build()
            return@addInterceptor it.proceed(requestBuilder)
        }
        return clientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()
    }

    @Provides
    @Singleton
    fun provideMovieApi(retrofit: Retrofit): MovieApi = retrofit.create(MovieApi::class.java)
}