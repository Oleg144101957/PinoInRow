package com.chicken_road.wingames.util

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.webkit.CookieManager
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.activity.result.ActivityResultLauncher
import com.chicken_road.wingames.MainActivity

@SuppressLint("SetJavaScriptEnabled")
class CustomViewAbout(
    context: MainActivity,
    onFileChoose: ContentChooser,
    content: ActivityResultLauncher<String>, onWhite: () -> Unit,
) : WebView(context) {

    init {
        layoutParams = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        webChromeClient = TapWebChromeClient(onFileChoose, content)
        webViewClient = TapWebViewClient(context, onWhite)
        settings.databaseEnabled = true
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.useWideViewPort = true
        settings.allowContentAccess = true
        settings.allowFileAccess = true
        requestFocus()
        settings.defaultTextEncodingName = "UTF-8"
        settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        isVerticalScrollBarEnabled = true
        isHorizontalScrollBarEnabled = true
        CookieManager.getInstance().setAcceptThirdPartyCookies(this, true)
        CookieManager.getInstance().setAcceptCookie(true)
    }

    fun getCurrentUrl(): String? {
        return url
    }
}