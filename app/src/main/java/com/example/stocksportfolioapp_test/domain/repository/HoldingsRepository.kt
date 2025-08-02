package com.example.stocksportfolioapp_test.domain.repository

import com.example.stocksportfolioapp_test.data.remote.dto.HoldingsListDto
import com.example.stocksportfolioapp_test.data.remote.dto.UserHoldingDto
import com.example.stocksportfolioapp_test.domain.model.UserHoldings
import com.example.stocksportfolioapp_test.domain.util.Results

interface HoldingsRepository {
    suspend fun getHoldings(): Results<List<UserHoldings>>
}