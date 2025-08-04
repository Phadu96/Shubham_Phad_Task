package com.example.stocksportfolioapp_test.data.repository

import com.example.stocksportfolioapp_test.data.local_db.HoldingsDao
import com.example.stocksportfolioapp_test.data.remote.api.HoldingsApi
import com.example.stocksportfolioapp_test.data.remote.mapper.toDomain
import com.example.stocksportfolioapp_test.data.remote.mapper.toEntity
import com.example.stocksportfolioapp_test.domain.model.UserHoldings
import com.example.stocksportfolioapp_test.domain.repository.HoldingsRepository
import com.example.stocksportfolioapp_test.domain.util.Results
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HoldingsRepositoryImpl(
    private val holdingsApi: HoldingsApi,
    private val networkConnectionImpl: NetworkConnectionImpl,
    private val holdingsDao: HoldingsDao
) : HoldingsRepository {
    override suspend fun getHoldings(): Results<List<UserHoldings>> {
        return try {
            if (networkConnectionImpl.isNetworkConnected()) {
                val response = holdingsApi.getHoldings()
                if (response.isSuccessful && response.body() != null) {
                    val remoteData =
                        response.body()?.data?.userHolding?.map { it.toEntity() } ?: emptyList()
                    withContext(Dispatchers.IO) {
                        holdingsDao.delete()
                        if (remoteData.isNotEmpty()) {
                            holdingsDao.insertAll(remoteData)
                        }
                    }
                }
            }

            val localData = withContext(Dispatchers.IO) {
                holdingsDao.getAll().map { it.toDomain() }
            }
            Results.Success(localData)

        } catch (e: Exception) {
            val fallbackData = withContext(Dispatchers.IO) {
                holdingsDao.getAll().map { it.toDomain() }
            }
            if (fallbackData.isNotEmpty()) {
                Results.Success(fallbackData)
            } else {
                Results.Failure(e.localizedMessage ?: "Unknown error")
            }
        }
    }
}