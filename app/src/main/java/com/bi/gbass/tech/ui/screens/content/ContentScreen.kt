package com.bi.gbass.tech.ui.screens.content

import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Build
import android.view.View
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.bi.gbass.tech.MainActivity
import com.bi.gbass.tech.data.NetworkCheckerRepositoryImpl
import com.bi.gbass.tech.navigation.ScreenRoutes
import com.bi.gbass.tech.util.lockOrientation
import com.bi.gbass.tech.util.web.MainCustomWebView
import com.bi.gbass.tech.util.web.MainWebChromeClient


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ContentScreen(
    navigationController: NavHostController,
    paddingValues: PaddingValues,
    url: String
) {
    val isWebViewVisible = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val activity = context as MainActivity
    activity.lockOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)

    val webView = remember { mutableStateOf<MainCustomWebView?>(null) }
    val destination = remember { mutableStateOf(url) }
    val networkChecker = NetworkCheckerRepositoryImpl(context)

    // File chooser launcher
    val fileChooserLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris: List<Uri> ->
        webView.value?.let { webViewInstance ->
            (webViewInstance.webChromeClient as? MainWebChromeClient)?.onFileCallback(uris.toTypedArray())
        }
    }

    // Обработчик кнопки назад
    BackHandler {
        webView.value?.let {
            val child = it.getChildAt(0)
            if (child != null) {
                it.removeView(child)
            } else {
                if (it.canGoBack()) {
                    it.goBack()
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
            .padding(paddingValues)
    ) {
        if (!isWebViewVisible.value) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        destination.value.let { url ->
            AndroidView(
                modifier = Modifier
                    .fillMaxSize()
                    .imePadding(),
                factory = {
                    // создаем экземпляр MainCustomWebView с твоими параметрами
                    MainCustomWebView(
                        context = activity,
                        content = fileChooserLauncher,
                        onWhite = {
                            navigationController.navigate(ScreenRoutes.HomeScreen.route)
                        },
                        onShowWeb = { isWebViewVisible.value = true }
                    ).apply {
                        webView.value = this

                        if (networkChecker.isConnectionAvailable()) {
                            loadUrl(url)
                        } else {
                            navigationController.navigate(ScreenRoutes.NoNetworkScreen.route)
                        }
                    }
                },
                update = { view ->
                    view.visibility = if (isWebViewVisible.value) View.VISIBLE else View.INVISIBLE
                }
            )
        }
    }
}

