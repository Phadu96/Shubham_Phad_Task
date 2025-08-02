package com.example.stocksportfolioapp_test.di

import com.example.stocksportfolioapp_test.presentation.stock_holdings.StockHoldingViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { StockHoldingViewModel(get(),get()) }
}