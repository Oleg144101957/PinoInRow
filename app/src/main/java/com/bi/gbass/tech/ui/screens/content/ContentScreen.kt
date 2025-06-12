package com.bi.gbass.tech.ui.screens.content

import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    url: String
) {
    val context = LocalContext.current
    val onWhite = remember {
        mutableStateOf(false)
    }

    val activity = context as MainActivity
    activity.lockOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
    val webView = remember { mutableStateOf<MainCustomWebView?>(null) }
    val destination = remember { mutableStateOf(url) }
    val networkChecker = NetworkCheckerRepositoryImpl(context)
    val fileChooserLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris: List<Uri> ->
        webView.value?.let { webViewInstance ->
            (webViewInstance.webChromeClient as? MainWebChromeClient)?.onFileCallback(uris.toTypedArray())
        }
    }

    BackHandler {
        webView.value?.let {
            val child = it.getChildAt(0)
            if (child != null) {
                //we need to close child
                it.removeView(child)
            } else {
                //we don't have child WebView
                if (it.canGoBack()) {
                    it.goBack()
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        destination.value.let { url ->
            AndroidView(
                modifier = Modifier
                    .fillMaxSize()
                    .imePadding(),
                factory = {
                    MainCustomWebView(activity, fileChooserLauncher, onWhite = {
                        onWhite.value = true
                    }).also {
                        webView.value = it
                        if (networkChecker.isConnectionAvailable()) {
                            it.loadUrl(url)
                        } else {
                            navigationController.navigate(ScreenRoutes.NoNetworkScreen.route)
                        }
                    }
                }
            )
        }
        if (onWhite.value) {
            Button(
                onClick = {
                    navigationController.navigate(ScreenRoutes.HomeScreen.route)
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 64.dp, end = 16.dp)
            ) {
                Text(text = "Next")
            }
        }
    }
}
