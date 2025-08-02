package com.example.stocksportfolioapp_test.data.remote.dto

import com.google.gson.annotations.SerializedName

data class HoldingsResponse(
    @SerializedName("data") var data: HoldingsListDto = HoldingsListDto()

)
