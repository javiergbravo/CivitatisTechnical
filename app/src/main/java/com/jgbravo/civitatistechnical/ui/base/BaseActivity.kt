package com.jgbravo.civitatistechnical.ui.base

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.jgbravo.civitatistechnical.R

abstract class BaseActivity : AppCompatActivity() {

    private lateinit var container: ConstraintLayout
    private lateinit var loader: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onPostCreate()
    }

    private fun onPostCreate() {
        setContentView(getLayoutID())
        bindViews()
        configureViews()
    }

    @LayoutRes
    protected abstract fun getLayoutID(): Int

    protected open fun bindViews() {
        container = findViewById(R.id.container)
        loader = findViewById(R.id.loader)
    }

    protected abstract fun configureViews()

    protected fun showLoader() {
        container.visibility = View.GONE
        loader.visibility = View.VISIBLE
    }

    protected fun hideLoader() {
        container.visibility = View.VISIBLE
        loader.visibility = View.GONE
    }
}