package com.thakurnitin2684.codeforces.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.thakurnitin2684.codeforces.BaseApplication
import com.thakurnitin2684.codeforces.R


class ForWebViewHome : Fragment() {
    private lateinit var mWeb: WebView

    private lateinit var baseContext : BaseApplication

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.web_view, container, false)

        baseContext = (requireActivity().application as BaseApplication)

        val bundle = arguments
        val url= bundle?.getString("url")
        mWeb = root.findViewById(R.id.webView1)
        mWeb.webViewClient = WebViewClient()
        val webSettings: WebSettings = mWeb.settings
        webSettings.builtInZoomControls = true
        webSettings.javaScriptEnabled = true
        webSettings.useWideViewPort=true
        mWeb.loadUrl("$url?mobile=${baseContext.getMobileView()}")
        return root
    }


    fun canGoBack(): Boolean {
        return mWeb.canGoBack()
    }

    fun goBack() {
        mWeb.goBack()
    }


}