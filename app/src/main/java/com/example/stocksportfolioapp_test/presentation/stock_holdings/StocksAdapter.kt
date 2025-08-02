package com.example.stocksportfolioapp_test.presentation.stock_holdings

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.stocksportfolioapp_test.R
import com.example.stocksportfolioapp_test.databinding.ItemStocksBinding
import com.example.stocksportfolioapp_test.domain.model.UserHoldings
import com.example.stocksportfolioapp_test.presentation.base.BaseRecyclerViewAdapter
import com.example.stocksportfolioapp_test.utils.formatToRupees

class StocksAdapter(
    items: List<UserHoldings>,
    private val onClick: (UserHoldings) -> Unit
) : BaseRecyclerViewAdapter<UserHoldings, ItemStocksBinding>(items) {

    override fun createBinding(parent: ViewGroup): ItemStocksBinding {
        val inflater = LayoutInflater.from(parent.context)
        return ItemStocksBinding.inflate(inflater, parent, false)
    }

    @SuppressLint("SetTextI18n")
    override fun bindData(binding: ItemStocksBinding, item: UserHoldings, position: Int) {
        val context = binding.root.context

        binding.tvStockName.text = item.symbol
        binding.tvStockQtyValue.text = item.quantity.toString()
        binding.tvStockLtpValue.text = item.ltp.formatToRupees(context)
        val totalPnL = (item.ltp * item.quantity) - (item.avgPrice * item.quantity)
        binding.tvStockPlValue.text =
            totalPnL.formatToRupees(context)
        val colorRes = if (totalPnL > 0) R.color.clr_profit else R.color.clr_loss
        binding.tvStockPlValue.setTextColor(ContextCompat.getColor(context, colorRes))
    }
}