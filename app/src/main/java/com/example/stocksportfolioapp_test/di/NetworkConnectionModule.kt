package com.example.stocksportfolioapp_test.di

import com.example.stocksportfolioapp_test.data.repository.NetworkConnectionImpl
import org.koin.dsl.module

val networkConnectionModule = module {
    single { NetworkConnectionImpl(get()) }
}