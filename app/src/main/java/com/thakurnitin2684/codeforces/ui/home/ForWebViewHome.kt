package com.thakurnitin2684.codeforces.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.thakurnitin2684.codeforces.R
import com.thakurnitin2684.codeforces.mobileVw


private const val TAG = "ForWebView"

class ForWebViewHome : Fragment() {
    private lateinit var mWeb: WebView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView started")
        val root = inflater.inflate(R.layout.web_view, container, false)

        val bundle = arguments
        val url= bundle?.getString("url")
        mWeb = root.findViewById(R.id.webView1)
        mWeb.webViewClient = WebViewClient()
        val webSettings: WebSettings = mWeb.settings
        webSettings.builtInZoomControls = true
        webSettings.javaScriptEnabled = true
        webSettings.useWideViewPort=true
        mWeb.loadUrl("$url?mobile=$mobileVw")
        return root
    }


    fun canGoBack(): Boolean {
        return mWeb.canGoBack()
    }

    fun goBack() {
        mWeb.goBack()
    }


}