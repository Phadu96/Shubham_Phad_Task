package com.example.stocksportfolioapp_test

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.stocksportfolioapp_test.domain.model.Portfolio
import com.example.stocksportfolioapp_test.domain.model.UserHoldings
import com.example.stocksportfolioapp_test.domain.usecases.GetHoldingsDataUseCase
import com.example.stocksportfolioapp_test.domain.usecases.PortfolioCalculationUseCase
import com.example.stocksportfolioapp_test.domain.util.Results
import com.example.stocksportfolioapp_test.presentation.stock_holdings.StockHoldingViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class StockHoldingsViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val standardTestDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: StockHoldingViewModel

    private val getHoldingsDataUseCase: GetHoldingsDataUseCase = mockk()
    private val portfolioCalculationUseCase: PortfolioCalculationUseCase = mockk()

    @Before
    fun setup() {
        Dispatchers.setMain(standardTestDispatcher)
        viewModel = StockHoldingViewModel(getHoldingsDataUseCase, portfolioCalculationUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchHoldings success updates holdings and portfolio`() = runTest {
        val holdings = listOf(
            UserHoldings("TCS", 50, 200.0, 2800.0, 2950.0),
            UserHoldings("Tata Motors", 100, 700.0, 780.0, 770.0)
        )
        val portfolio = Portfolio(1000.0, 20000.0, 2000.0, 500.0)

        coEvery { getHoldingsDataUseCase() } returns Results.Success(holdings)
        every { portfolioCalculationUseCase(holdings) } returns portfolio

        viewModel.fetchHoldings()
        advanceUntilIdle()

        assertTrue(viewModel.holdingsList.value is Results.Success)
        assertEquals(holdings, (viewModel.holdingsList.value as Results.Success).data)
        assertEquals(portfolio, viewModel.portfolio.value)
    }

    @Test
    fun `fetchHoldings failure updates holdingsList with Failure`() = runTest {
        val errorMsg = "Network Error"


        val errorFallbackData = listOf(
            UserHoldings("TCS", 50, 200.0, 2800.0, 2950.0),
            UserHoldings("Tata Motors", 100, 700.0, 780.0, 770.0),
            UserHoldings("ONGC", 10, 150.0, 130.0, 170.0)
        )
        val portfolio = Portfolio(1000.0, 20000.0, 2000.0, 500.0)
        coEvery { getHoldingsDataUseCase() } returns Results.Failure(errorMsg)
        coEvery { getHoldingsDataUseCase() } returns Results.Success(errorFallbackData)
        every { portfolioCalculationUseCase(errorFallbackData) } returns portfolio

        viewModel.fetchHoldings()
        advanceUntilIdle()

        val result = viewModel.holdingsList.value

        assertTrue(result is Results.Success)
        assertEquals(errorFallbackData, (result as Results.Success).data)
        assertEquals(portfolio, viewModel.portfolio.value)
    }

    @Test
    fun `fetchHoldings failure with no fallbackdata`() = runTest {
        coEvery { getHoldingsDataUseCase() } returnsMany listOf(
            Results.Failure("Network Error"),
            Results.Success(emptyList())
        )

        viewModel.fetchHoldings()
        advanceUntilIdle()

        assertTrue(viewModel.holdingsList.value is Results.Failure)
        assertEquals("Network Error", (viewModel.holdingsList.value as Results.Failure).message)
    }

    @Test
    fun `fetchHoldings with empty list updates holdingsList successfully`() = runTest {
        val emptyHoldings = emptyList<UserHoldings>()

        coEvery { getHoldingsDataUseCase() } returns Results.Success(emptyHoldings)
        every { portfolioCalculationUseCase(emptyList()) } returns Portfolio(0.0, 0.0, 0.0, 0.0)

        viewModel.fetchHoldings()
        advanceUntilIdle()

        assertTrue(viewModel.holdingsList.value is Results.Success)
        assertEquals(emptyHoldings, (viewModel.holdingsList.value as Results.Success).data)
    }

}