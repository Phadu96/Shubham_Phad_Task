package com.example.stocksportfolioapp_test.di

import com.example.stocksportfolioapp_test.domain.usecases.GetHoldingsDataUseCase
import com.example.stocksportfolioapp_test.domain.usecases.PortfolioCalculationUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { GetHoldingsDataUseCase(get()) }
    single { PortfolioCalculationUseCase() }
}