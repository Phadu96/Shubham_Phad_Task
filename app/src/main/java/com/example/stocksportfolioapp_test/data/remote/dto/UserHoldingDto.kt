package com.example.stocksportfolioapp_test.data.remote.dto

import com.google.gson.annotations.SerializedName

data class UserHoldingDto(
    @SerializedName("symbol") var symbol: String,
    @SerializedName("quantity") var quantity: Int,
    @SerializedName("ltp") var ltp: Double,
    @SerializedName("avgPrice") var avgPrice: Double,
    @SerializedName("close") var close: Double
)