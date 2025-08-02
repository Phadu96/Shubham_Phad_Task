package com.example.stocksportfolioapp_test.presentation.stock_holdings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stocksportfolioapp_test.domain.model.Portfolio
import com.example.stocksportfolioapp_test.domain.model.UserHoldings
import com.example.stocksportfolioapp_test.domain.usecases.GetHoldingsDataUseCase
import com.example.stocksportfolioapp_test.domain.usecases.PortfolioCalculationUseCase
import com.example.stocksportfolioapp_test.domain.util.Results
import kotlinx.coroutines.launch

class StockHoldingViewModel(
    private val getHoldingsDataUseCase: GetHoldingsDataUseCase,
    private val portfolioCalculationUseCase: PortfolioCalculationUseCase
) : ViewModel() {
    private val _holdingsList = MutableLiveData<Results<List<UserHoldings>>>()
    val holdingsList: LiveData<Results<List<UserHoldings>>> = _holdingsList

    private val _portfolio = MutableLiveData<Portfolio>()
    val portfolio: LiveData<Portfolio> = _portfolio

    fun fetchHoldings() {
        viewModelScope.launch {
            _holdingsList.value = Results.Loading
            val result = getHoldingsDataUseCase()
            _holdingsList.value = result
            if (result is Results.Success) {
                val portfolioSummary = portfolioCalculationUseCase(result.data)
                _portfolio.value = portfolioSummary
            }
        }
    }

}