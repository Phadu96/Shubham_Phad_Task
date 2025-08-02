package com.example.stocksportfolioapp_test.utils

import android.content.Context
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.stocksportfolioapp_test.R
import com.facebook.shimmer.ShimmerFrameLayout
import java.text.NumberFormat
import java.util.Locale


fun ProgressBar.showLoading(targetView: View?, show: Boolean) {
    isVisible = show
    targetView?.isVisible = !show
}

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Double.formatToRupees(context: Context): String {
    val rupee = context.getString(R.string.rupee_symbol)
    val locale = Locale.Builder().setLanguage("en").setRegion("IN").build()
    val formatter = NumberFormat.getNumberInstance(locale).apply {
        minimumFractionDigits = 2
        maximumFractionDigits = 2
    }
    return "$rupee ${formatter.format(this)}"
}

fun ShimmerFrameLayout.startShimmering(showView: Boolean = true) {
    this.startShimmer()
    this.visibility = if (showView) View.VISIBLE else View.GONE
}

fun ShimmerFrameLayout.stopShimmering(hideView: Boolean = true) {
    this.stopShimmer()
    this.visibility = if (hideView) View.GONE else View.VISIBLE
}