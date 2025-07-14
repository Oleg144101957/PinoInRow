package com.boo.me.ran.g.ui.screens.splash

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.boo.me.ran.g.R
import com.boo.me.ran.g.navigation.ScreenRoutes
import com.boo.me.ran.g.ui.custom.Background
import com.boo.me.ran.g.util.lockOrientation
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavHostController,
    viewModel: SplashViewModel = hiltViewModel()
) {

    BackHandler { }
    val context = LocalContext.current
    val activity = context as? Activity
    activity?.lockOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    val state = viewModel.liveState.collectAsState().value
    LaunchedEffect(Unit) {
        delay(3000)
        navController.navigate(ScreenRoutes.HomeScreen.route)
    }
//    val permission = android.Manifest.permission.POST_NOTIFICATIONS
//    val permissionState = remember { mutableStateOf(false) }
//
//    val launcher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.RequestPermission()
//    ) { isGranted ->
//        permissionState.value = isGranted
//        viewModel.updatePermissionState(isGranted)
//    }
//
//    LaunchedEffect(Unit) {
//        launcher.launch(permission)
//    }
//
//    LaunchedEffect(state) {
//        when (state) {
//            LoadingState.InitState -> {
//                viewModel.load(context)
//            }
//
//            LoadingState.NoNetworkState -> {
//                navController.navigate(ScreenRoutes.NoNetworkScreen.route) {
//                    popUpTo(0)
//                }
//            }
//
//            LoadingState.MenuState -> {
//                delay(3000)
//                navController.navigate(ScreenRoutes.HomeScreen.route)
//            }
//
//            is LoadingState.ContentState -> {
//                val url = URLEncoder.encode(state.url, StandardCharsets.UTF_8.toString())
//                val route = "${ScreenRoutes.ContentScreen.route}/$url"
//                navController.navigate(route)
//            }
//        }
//    }

    Box(modifier = Modifier.fillMaxSize()) {
        Background(R.drawable.bg_game)
        Box(
            Modifier
                .fillMaxSize()
                .padding(top = 24.dp), contentAlignment = Alignment.TopCenter
        ) {
            Text(
                text = stringResource(R.string.app_name),
                color = Color.White,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Box(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 64.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    color = Color.Magenta
                )
            }
        }
    }
}



