package com.jesusd0897.hackernews.di

import android.content.Context
import com.jesusd0897.hackernews.data.network.api.ApiService
import com.jesusd0897.hackernews.data.network.api.RetrofitService
import com.jesusd0897.hackernews.data.network.util.RequestHandler
import com.jesusd0897.hackernews.data.network.util.RequestHandlerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    private const val BASE_URL = "https://hn.algolia.com"
    private const val MAX_REQUEST_COUNT = 4
    private const val TIME_OUT_CONNECT = 25L
    private const val TIME_OUT_WRITE = 25L
    private const val TIME_OUT_READ = 25L

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(RetrofitService::class.java)

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, converterFactory: Converter.Factory): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(converterFactory)
            .client(okHttpClient)
            .build()

    @Singleton
    @Provides
    fun provideConverter(): Converter.Factory = GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideOkHttpClient(dispatcher: Dispatcher): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(TIME_OUT_CONNECT, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT_WRITE, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT_READ, TimeUnit.SECONDS)
            .dispatcher(dispatcher)
            .build()

    @Singleton
    @Provides
    fun provideDispatcher() = Dispatcher().apply { maxRequests = MAX_REQUEST_COUNT }

    @Provides
    fun provideRequestHandler(@ApplicationContext context: Context): RequestHandler =
        RequestHandlerImpl(context)

}