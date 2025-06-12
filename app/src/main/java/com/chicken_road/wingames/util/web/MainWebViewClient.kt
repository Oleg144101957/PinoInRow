package com.chicken_road.wingames.util.web

import android.content.Intent
import android.util.Log
import android.webkit.CookieManager
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.net.toUri
import com.chicken_road.wingames.MainActivity

class MainWebViewClient(
    private val activity: MainActivity,
    private val onWhite: () -> Unit
) : WebViewClient() {

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        Log.d("123123", "Main WebView Parent URL is $url")
        CookieManager.getInstance().flush()
        if (url?.contains("r7z8KkNQ") == true) {
            onWhite.invoke()
        }
    }

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {

        Log.d("123123", "Should shouldOverrideUrlLoading ${request?.url}")

        // For diia
        if (request?.url.toString().contains("diia.app")) {
            try {
                val intentDiia = Intent(Intent.ACTION_VIEW)
                val dataForIntent = request?.url.toString().replaceFirst("https://", "diia://")
                intentDiia.setData(dataForIntent.toUri())
                activity.startActivity(intentDiia)
            } catch (e: Exception) {

            }
        }

        return super.shouldOverrideUrlLoading(view, request)
    }
}