package com.example.stocksportfolioapp_test.presentation

import android.os.Bundle
import com.example.stocksportfolioapp_test.presentation.base.BaseActivity
import com.example.stocksportfolioapp_test.utils.NavigationFragmentSupport
import com.example.stocksportfolioapp_test.R
import com.example.stocksportfolioapp_test.databinding.ActivityStocksPortfolioBinding
import androidx.core.view.get

class StocksPortfolioActivity :
    BaseActivity<ActivityStocksPortfolioBinding>(ActivityStocksPortfolioBinding::inflate),
    NavigationFragmentSupport {

    override fun navId() = R.id.nav_host_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.bottomNavigation.selectedItemId = binding.bottomNavigation.menu[2].itemId
    }

    fun setToolbar(
        header: String,
        search: Boolean = false
    ) {
        binding.toolbar.tvTitle.text = header

    }
}