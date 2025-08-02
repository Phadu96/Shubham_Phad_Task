package com.example.stocksportfolioapp_test.domain.util

sealed class Results<out T : Any?> {
    class Success<out T : Any?>(val data: T) : Results<T>()
    class Failure(val message: String) : Results<Nothing>()
    object Loading : Results<Nothing>()
}