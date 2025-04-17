package com.chicken_road.wingames.ui.screens.content

import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.chicken_road.wingames.MainActivity
import com.chicken_road.wingames.R
import com.chicken_road.wingames.data.DataStoreRepositoryImpl
import com.chicken_road.wingames.data.NetworkCheckerRepositoryImpl
import com.chicken_road.wingames.navigation.ScreenRoutes
import com.chicken_road.wingames.util.ContentChooser
import com.chicken_road.wingames.util.CustomViewAbout
import com.chicken_road.wingames.util.TapWebChromeClient
import com.chicken_road.wingames.util.lockOrientation


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ContentScreen(
    navigationController: NavHostController
) {

    val context = LocalContext.current

    val onWhite = remember {
        mutableStateOf(false)
    }

    val activity = context as MainActivity

    activity.lockOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)

    val webView = remember { mutableStateOf<CustomViewAbout?>(null) }
    val destination = remember { mutableStateOf<String?>(null) }
    val networkChecker = NetworkCheckerRepositoryImpl(context)


    val fileChooserLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        webView.value?.let { webViewInstance ->
            (webViewInstance.webChromeClient as? TapWebChromeClient)?.onFileCallback(arrayOf(uri))
        }
    }

    val contentChooser = remember {
        object : ContentChooser {
            override fun onFileCallback(parameters: Array<Uri?>) {
                webView.value?.let { webViewInstance ->
                    (webViewInstance.webChromeClient as? TapWebChromeClient)?.onFileCallback(
                        parameters
                    )
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        val userDataStorageImpl = DataStoreRepositoryImpl(context)
        destination.value = userDataStorageImpl.getUrl()
        Log.v("123123", "${destination.value}")
    }

    BackHandler {
        webView.value?.let {
            if (it.canGoBack()) {
                it.goBack()
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.bg),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )

        destination.value?.let { url ->
            AndroidView(
                modifier = Modifier
                    .fillMaxSize()
                    .imePadding(),
                factory = {
                    CustomViewAbout(activity, contentChooser, fileChooserLauncher, onWhite = {
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
                    .padding(16.dp)
            ) {
                Text(text = "Next")
            }
        }

    }
}
