package com.example.stocksportfolioapp_test.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.stocksportfolioapp_test.utils.NavigationFragmentSupport
import java.lang.IllegalStateException

abstract class BaseActivity<B : ViewBinding>(private val inflate: Inflate<B>) :
    AppCompatActivity() {

    val binding: B by lazy { inflate.invoke(LayoutInflater.from(this@BaseActivity), null, false) }

    val navHostFragment: NavHostFragment? by lazy {
        (this@BaseActivity as? NavigationFragmentSupport)?.let {
            supportFragmentManager.findFragmentById(it.navId()) as NavHostFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    fun navigateToPage(resId: Int, bundle: Bundle? = null) {
        if (navHostFragment == null) throw IllegalStateException("You must implement SupportNavigationFragment to have this feature")

        navHostFragment?.findNavController()?.navigate(resId, bundle)
    }
}
