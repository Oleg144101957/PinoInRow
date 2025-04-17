package com.chicken_road.wingames.util

import android.net.Uri
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.activity.result.ActivityResultLauncher

class TapWebChromeClient(
    private val onFileChoose: ContentChooser,
    private val content: ActivityResultLauncher<String>,
) : WebChromeClient() {

    private var filePathCallback: ValueCallback<Array<Uri?>>? = null

    override fun onShowFileChooser(
        webView: WebView?,
        filePathCallback: ValueCallback<Array<Uri?>>,
        fileChooserParams: FileChooserParams?
    ): Boolean {
        // Сохраняем ссылку на filePathCallback
        this.filePathCallback = filePathCallback
        // Вызываем выбор контента
        content.launch("image/*")
        return true
    }

    fun onFileCallback(uris: Array<Uri?>) {
        // Передаем выбранные файлы в filePathCallback
        filePathCallback?.onReceiveValue(uris)
        // Очищаем filePathCallback после обработки
        filePathCallback = null
    }
}

interface ContentChooser {
    fun onFileCallback(parameters: Array<Uri?>)
}
