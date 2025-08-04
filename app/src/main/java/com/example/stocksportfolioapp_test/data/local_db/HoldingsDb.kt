package com.example.stocksportfolioapp_test.data.local_db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [HoldingsEntity::class], version = 1,exportSchema = false)
abstract class HoldingsDb : RoomDatabase() {
    abstract fun holdingsDao(): HoldingsDao
}