package com.example.stocksportfolioapp_test.domain.usecases

import com.example.stocksportfolioapp_test.domain.model.Portfolio
import com.example.stocksportfolioapp_test.domain.model.UserHoldings

class PortfolioCalculationUseCase {
    operator fun invoke(holdings: List<UserHoldings>): Portfolio {
        val currentValue = holdings.sumOf { it.ltp * it.quantity }
        val totalInvestment = holdings.sumOf { it.avgPrice * it.quantity }
        val totalPnL = currentValue - totalInvestment
        val todaysPnL = holdings.sumOf { (it.ltp - it.close) * it.quantity }
        return Portfolio(currentValue, totalInvestment, totalPnL, todaysPnL)
    }
}