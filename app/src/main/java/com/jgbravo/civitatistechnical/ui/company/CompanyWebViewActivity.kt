package com.jgbravo.civitatistechnical.ui.company

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.*
import com.jgbravo.civitatistechnical.R
import com.jgbravo.civitatistechnical.ui.NavigationConstants
import com.jgbravo.civitatistechnical.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CompanyWebViewActivity : BaseActivity() {

    private lateinit var webview: WebView

    override fun getLayoutID(): Int = R.layout.activity_company_webview
    override fun toolbarTitle(): Int = R.string.company_title

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showLoader()
        loadBundles()
    }

    override fun setUpToolbar() {
        super.setUpToolbar()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun bindViews() {
        super.bindViews()
        webview = findViewById(R.id.webview)
    }

    private fun loadBundles() {
        if (intent.hasExtra(NavigationConstants.LINK_BUNDLE)) {
            intent.extras?.getString(NavigationConstants.LINK_BUNDLE)?.let {
                configureWebView(it)
            }
        }
    }

    override fun configureViews() {}

    @SuppressLint("SetJavaScriptEnabled")
    private fun configureWebView(url: String) {
        webview.settings.setJavaScriptEnabled(true)
        webview.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return super.shouldOverrideUrlLoading(view, request)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                hideLoader()
            }

            override fun onReceivedHttpError(
                view: WebView?,
                request: WebResourceRequest?,
                errorResponse: WebResourceResponse?
            ) {
                super.onReceivedHttpError(view, request, errorResponse)
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
            }

        }
        webview.loadUrl(url)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}