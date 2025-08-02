package com.example.stocksportfolioapp_test.presentation.stock_holdings

import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stocksportfolioapp_test.R
import com.example.stocksportfolioapp_test.databinding.FragmentStocksHoldingsBinding
import com.example.stocksportfolioapp_test.domain.model.UserHoldings
import com.example.stocksportfolioapp_test.domain.util.Results
import com.example.stocksportfolioapp_test.presentation.StocksPortfolioActivity
import com.example.stocksportfolioapp_test.presentation.base.BaseFragment
import com.example.stocksportfolioapp_test.utils.formatToRupees
import com.example.stocksportfolioapp_test.utils.showLoading
import com.example.stocksportfolioapp_test.utils.showToast
import com.example.stocksportfolioapp_test.utils.startShimmering
import com.example.stocksportfolioapp_test.utils.stopShimmering
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

class StockHoldingFragment :
    BaseFragment<FragmentStocksHoldingsBinding>(FragmentStocksHoldingsBinding::inflate) {
    private val stockHoldingViewModel by viewModel<StockHoldingViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbar(getString(R.string.portfolio))
        getData()
        setObservers()
    }

    fun getData() {
        binding.shimmerViewContainer.startShimmering()
        stockHoldingViewModel.fetchHoldings()
    }

    fun setObservers() {
        stockHoldingViewModel.holdingsList.observe(viewLifecycleOwner) {
            when (it) {
                is Results.Loading -> {
                    binding.shimmerViewContainer.startShimmering()
                    (requireActivity() as StocksPortfolioActivity).binding.progressBar.showLoading(
                        binding.rvStocks,
                        true
                    )
                }

                is Results.Success -> {
                    binding.shimmerViewContainer.stopShimmering()
                    (requireActivity() as StocksPortfolioActivity).binding.progressBar.showLoading(
                        binding.rvStocks,
                        false
                    )
                    binding.tvNoData.isVisible = it.data.isEmpty()
                    binding.clyBottomSheetPnl.isVisible = it.data.isNotEmpty()
                    binding.clyPnl.isVisible = it.data.isNotEmpty()
                    setStocksList(it.data)
                }

                is Results.Failure -> {
                    binding.shimmerViewContainer.stopShimmering()
                    (requireActivity() as StocksPortfolioActivity).binding.progressBar.showLoading(
                        binding.rvStocks,
                        false
                    )
                    requireContext().showToast(it.message)
                }
            }
        }

        stockHoldingViewModel.portfolio.observe(viewLifecycleOwner) { portfolio ->
            binding.bottomSheetProfitLoss.tvCurrentValueAmt.text =
                portfolio.currentValue.formatToRupees(requireContext())
            binding.bottomSheetProfitLoss.tvInvestmentValue.text =
                portfolio.totalInvestment.formatToRupees(requireContext())
            binding.bottomSheetProfitLoss.tvTodaysPnlValue.text =
                portfolio.todaysPnL.formatToRupees(requireContext())
            val color = if (portfolio.todaysPnL > 0) R.color.clr_profit else R.color.clr_loss
            binding.bottomSheetProfitLoss.tvTodaysPnlValue.setTextColor(
                requireContext().getColor(
                    color
                )
            )
            binding.tvPnlValue.text = portfolio.totalPnL.formatToRupees(requireContext())
            val colorRes = if (portfolio.totalPnL > 0) R.color.clr_profit else R.color.clr_loss
            binding.tvPnlValue.setTextColor(requireContext().getColor(colorRes))
        }
    }

    private fun setStocksList(stocksList: List<UserHoldings>) {
        bottomSheetDialog()
        val stocksAdapter = StocksAdapter(
            stocksList
        ) {

        }

        binding.rvStocks.apply {
            adapter = stocksAdapter
//            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun bottomSheetDialog() {
        val bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout> =
            BottomSheetBehavior.from(binding.clyBottomSheetPnl)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomSheetBehavior.isHideable = true

        binding.clyPnl.post {
            val pnlHeight = binding.clyPnl.height
            bottomSheetBehavior.peekHeight = 0
            binding.clyBottomSheetPnl.setPadding(0, 0, 0, pnlHeight)
        }
        binding.clyPnl.setOnClickListener {
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
                binding.clyPnl.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.bg_bottom_sheet_pnl)
                binding.ivSheetArrow.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_arrow_up
                    )
                )
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            } else {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                binding.clyPnl.background = null
                binding.ivSheetArrow.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_arrow_down
                    )
                )
            }
        }
    }
}