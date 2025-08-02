package com.example.stocksportfolioapp_test.di

import com.example.stocksportfolioapp_test.data.remote.api.HoldingsApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private fun provideHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        )
        .connectTimeout(30, TimeUnit.MINUTES)
        .readTimeout(30, TimeUnit.MINUTES)
        .writeTimeout(30, TimeUnit.MINUTES)
        .build()
}

private fun provideRetrofit(httpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://35dee773a9ec441e9f38d5fc249406ce.api.mockbin.io")
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

val networkModule = module {
    single { provideHttpClient() }
    single { provideRetrofit(get()) }
    single<HoldingsApi> { get<Retrofit>().create(HoldingsApi::class.java) }
}