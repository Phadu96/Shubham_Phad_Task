package com.example.stocksportfolioapp_test.domain.model

data class UserHoldings (
    val symbol: String,
    val quantity: Int,
    val ltp: Double,
    val avgPrice: Double,
    val close: Double
)