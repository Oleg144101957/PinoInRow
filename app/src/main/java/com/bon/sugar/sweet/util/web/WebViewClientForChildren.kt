package com.bon.sugar.sweet.util.web

import android.util.Log
import android.view.View
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import com.bon.sugar.sweet.util.web.CustomWebViewForChildren
import com.bon.sugar.sweet.util.web.MainCustomWebView

class WebViewClientForChildren(
    private val parentWebView: MainCustomWebView,
    private val webViewChild: CustomWebViewForChildren,
) : WebViewClient() {

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)

        Log.d("123123", "onPageFinished for Children $url")

        val child: View? = parentWebView.getChildAt(0)

        if (child == null) {
            parentWebView.addView(webViewChild)
        }
        CookieManager.getInstance().flush()
    }

}