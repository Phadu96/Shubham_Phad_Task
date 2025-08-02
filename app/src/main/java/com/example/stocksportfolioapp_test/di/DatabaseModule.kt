package com.example.stocksportfolioapp_test.di

import android.app.Application
import androidx.room.Room
import com.example.stocksportfolioapp_test.data.local_db.HoldingsDao
import com.example.stocksportfolioapp_test.data.local_db.HoldingsDb
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            get<Application>(),
            HoldingsDb::class.java,
            "StockPortfolio"
        ).build()
    }

    single<HoldingsDao> { get<HoldingsDb>().holdingsDao() }
}