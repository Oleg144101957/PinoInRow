package com.chicken_road.wingames.ui.screens.splash

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.chicken_road.wingames.R
import com.chicken_road.wingames.domain.LoadingState
import com.chicken_road.wingames.navigation.ScreenRoutes
import com.chicken_road.wingames.ui.custom.Background
import com.chicken_road.wingames.util.lockOrientation

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


    LaunchedEffect(state) {
        when (state) {
            LoadingState.InitState -> {
                viewModel.load(context)
            }

            LoadingState.NoNetworkState -> {
                navController.navigate(ScreenRoutes.NoNetworkScreen.route) {
                    popUpTo(0)
                }
            }

            is LoadingState.ContentState -> {
                navController.navigate(ScreenRoutes.ContentScreen.route)
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Background(R.drawable.bg)
        Box(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(64.dp))
            Image(
                painterResource(R.drawable.logo),
                contentDescription = R.drawable.logo.toString(),
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth(),
                contentScale = ContentScale.FillBounds
            )
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
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}



