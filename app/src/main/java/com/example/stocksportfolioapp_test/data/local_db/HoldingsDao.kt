package com.example.stocksportfolioapp_test.data.local_db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HoldingsDao{
    @Query("SELECT * FROM Holdings")
    suspend fun getAll(): List<HoldingsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(holdings: List<HoldingsEntity>)

    @Query("DELETE FROM Holdings")
    suspend fun delete()
}