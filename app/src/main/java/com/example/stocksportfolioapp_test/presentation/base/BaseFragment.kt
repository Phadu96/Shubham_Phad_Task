package com.example.stocksportfolioapp_test.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.stocksportfolioapp_test.presentation.StocksPortfolioActivity

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB : ViewBinding>(private val inflate: Inflate<VB>) : Fragment() {
    private var _binding: VB? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    fun navigate(
        resId: Int,
        bundle: Bundle? = null,
        navigatorExtras: FragmentNavigator.Extras? = null
    ) {
        findNavController().navigate(resId, bundle, null, navigatorExtras)
    }

    fun goBack() = findNavController().popBackStack()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setToolbar(
        header: String,
        search: Boolean = false
    ) {
        (activity as StocksPortfolioActivity).setToolbar(header,search)
    }

}
