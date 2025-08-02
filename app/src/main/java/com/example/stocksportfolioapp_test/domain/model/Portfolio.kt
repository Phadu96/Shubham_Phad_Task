package com.example.stocksportfolioapp_test.domain.model

data class Portfolio(
    val currentValue: Double,
    val totalInvestment: Double,
    val totalPnL: Double,
    val todaysPnL: Double
)