package com.bi.gbass.tech.ui.screens

import android.content.pm.ActivityInfo
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bi.gbass.tech.MainActivity
import com.bi.gbass.tech.domain.GameType
import com.bi.gbass.tech.navigation.ScreenRoutes
import com.bi.gbass.tech.ui.custom.Background
import com.bi.gbass.tech.ui.custom.MenuButton
import com.bi.gbass.tech.util.lockOrientation

@Composable
fun HomeScreen(navController: NavHostController, innerPadding: PaddingValues) {
    BackHandler { }
    val context = LocalContext.current
    val activity = context as? MainActivity
    activity?.lockOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        contentAlignment = Alignment.Center
    ) {
        Background()
        Menu(navController, activity)
    }
}

@Composable
fun Menu(navController: NavHostController, activity: MainActivity?) {
    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        MenuButton("Fish Hunt", modifier = Modifier) {
            navController.navigate("${ScreenRoutes.GameScreen.route}/${GameType.FISH}")
        }
        Spacer(modifier = Modifier.height(16.dp))
        MenuButton("Shrimp Hunt", modifier = Modifier) {
            navController.navigate("${ScreenRoutes.GameScreen.route}/${GameType.SHRIMP}")
        }
        Spacer(modifier = Modifier.height(16.dp))
        MenuButton("Settings", modifier = Modifier) {
            navController.navigate(ScreenRoutes.SettingsScreen.route)
        }
        Spacer(modifier = Modifier.height(16.dp))
        MenuButton("Exit", modifier = Modifier) {
            activity?.finish()
        }
    }
}


