package com.ze.us.ga.m.util.web

import android.util.Log
import android.view.View
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import com.ze.us.ga.m.MainActivity

class WebViewClientForChildren(
    private val parentWebView: MainCustomWebView,
    private val webViewChild: CustomWebViewForChildren,
    private val activity: MainActivity
) : WebViewClient() {

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)

        Log.d("123123", "onPageFinished for Children")

        val child: View? = parentWebView.getChildAt(0)

        if (child == null) {
            parentWebView.addView(webViewChild)
        }
        CookieManager.getInstance().flush()
    }

}