package com.example.stocksportfolioapp_test.di

import com.example.stocksportfolioapp_test.data.repository.HoldingsRepositoryImpl
import com.example.stocksportfolioapp_test.domain.repository.HoldingsRepository
import org.koin.dsl.module


val repositoryModule = module {
    single<HoldingsRepository> { HoldingsRepositoryImpl(get(), get(), get()) }
}
