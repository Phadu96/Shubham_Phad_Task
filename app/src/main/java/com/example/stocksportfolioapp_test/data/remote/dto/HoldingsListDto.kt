package com.example.stocksportfolioapp_test.data.remote.dto

import com.google.gson.annotations.SerializedName

data class HoldingsListDto (
    @SerializedName("userHolding" ) var userHolding : ArrayList<UserHoldingDto> = arrayListOf()
)