package com.example.stocksportfolioapp_test.data.local_db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Holdings")
data class HoldingsEntity (
    @PrimaryKey val symbol: String,
    val quantity: Int,
    val ltp: Double,
    val avgPrice: Double,
    val close: Double
)
