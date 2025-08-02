package com.example.stocksportfolioapp_test.data.remote.mapper

import com.example.stocksportfolioapp_test.data.local_db.HoldingsEntity
import com.example.stocksportfolioapp_test.data.remote.dto.UserHoldingDto
import com.example.stocksportfolioapp_test.domain.model.UserHoldings


fun UserHoldingDto.toEntity(): HoldingsEntity {
    return HoldingsEntity(symbol, quantity, ltp, avgPrice, close)
}

fun HoldingsEntity.toDomain(): UserHoldings {
    return UserHoldings(symbol, quantity, ltp, avgPrice, close)
}