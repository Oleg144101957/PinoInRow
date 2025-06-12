package com.bi.gbass.tech.util.web

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.webkit.CookieManager
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.activity.result.ActivityResultLauncher
import com.bi.gbass.tech.MainActivity

@SuppressLint("SetJavaScriptEnabled")
class MainCustomWebView(
    context: MainActivity,
    content: ActivityResultLauncher<String>,
    onWhite: () -> Unit,
) : WebView(context) {

    init {
        layoutParams = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        webChromeClient = MainWebChromeClient(content, context, this)
        webViewClient = MainWebViewClient(context, onWhite)
        settings.databaseEnabled = true
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.useWideViewPort = true
        settings.allowContentAccess = true
        settings.allowFileAccess = true

        settings.builtInZoomControls = false
        settings.displayZoomControls = true
        settings.javaScriptCanOpenWindowsAutomatically = true
        settings.setSupportMultipleWindows(true)
        settings.setSupportZoom(true)


        requestFocus()
        settings.defaultTextEncodingName = "UTF-8"
        settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        isVerticalScrollBarEnabled = true
        isHorizontalScrollBarEnabled = true
        settings.userAgentString = settings.userAgentString.agentChanger()
        CookieManager.getInstance().setAcceptThirdPartyCookies(this, true)
        CookieManager.getInstance().setAcceptCookie(true)
    }


    private fun String.agentChanger(): String {
        return this.replace("wv", " ")
    }
}