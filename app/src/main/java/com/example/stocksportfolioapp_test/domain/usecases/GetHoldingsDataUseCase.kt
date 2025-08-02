package com.example.stocksportfolioapp_test.domain.usecases

import com.example.stocksportfolioapp_test.data.remote.dto.HoldingsListDto
import com.example.stocksportfolioapp_test.domain.model.UserHoldings
import com.example.stocksportfolioapp_test.domain.repository.HoldingsRepository
import com.example.stocksportfolioapp_test.domain.util.Results

class GetHoldingsDataUseCase(private val repository: HoldingsRepository) {
    suspend operator fun invoke(): Results<List<UserHoldings>> {
        return repository.getHoldings()
    }
}