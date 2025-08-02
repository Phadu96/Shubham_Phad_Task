package com.example.stocksportfolioapp_test

import android.app.Application
import com.example.stocksportfolioapp_test.di.databaseModule
import com.example.stocksportfolioapp_test.di.networkConnectionModule
import com.example.stocksportfolioapp_test.di.networkModule
import com.example.stocksportfolioapp_test.di.repositoryModule
import com.example.stocksportfolioapp_test.di.useCaseModule
import com.example.stocksportfolioapp_test.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class StocksPortfolioApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@StocksPortfolioApp)
            modules(
                networkModule,
                databaseModule,
                networkConnectionModule,
                repositoryModule,
                viewModelModule,
                useCaseModule
            )
        }
    }
}