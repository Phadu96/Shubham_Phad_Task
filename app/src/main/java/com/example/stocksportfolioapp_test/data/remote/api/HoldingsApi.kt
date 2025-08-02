package com.example.stocksportfolioapp_test.data.remote.api

import com.example.stocksportfolioapp_test.data.remote.dto.HoldingsListDto
import com.example.stocksportfolioapp_test.data.remote.dto.HoldingsResponse
import retrofit2.Response
import retrofit2.http.GET

interface HoldingsApi {

    @GET("/")
    suspend fun getHoldings(): Response<HoldingsResponse>

}