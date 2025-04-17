package com.chicken_road.wingames.util

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.webkit.CookieManager
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.chicken_road.wingames.MainActivity

class TapWebViewClient(
    private val activity: MainActivity,
    private val onWhite: () -> Unit
) : WebViewClient() {

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        CookieManager.getInstance().flush()
        Log.v("123123", "on page finished: $url")
        if (url?.contains("r7z8KkNQ") == true) {
            onWhite.invoke()
        }
    }

    override fun shouldOverrideUrlLoading(
        view: WebView?, request: WebResourceRequest?
    ): Boolean {
        val url = request?.url?.toString() ?: return super.shouldOverrideUrlLoading(view, request)
        if (!url.startsWith("http")) {
            return try {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                view?.context?.startActivity(intent)
                true
            } catch (e: ActivityNotFoundException) {
                view?.let {
                    Toast.makeText(
                        it.context, "${
                            url.substringBefore("://").uppercase()
                        } This link is not available", Toast.LENGTH_SHORT
                    ).show()
                }
                true
            }
        }

        return super.shouldOverrideUrlLoading(view, request)
    }
}