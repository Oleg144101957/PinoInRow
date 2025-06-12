package com.chicken_road.wingames.ui.screens

import android.app.Activity
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.chicken_road.wingames.MainActivity
import com.chicken_road.wingames.R
import com.chicken_road.wingames.domain.GameType
import com.chicken_road.wingames.navigation.ScreenRoutes
import com.chicken_road.wingames.ui.custom.Background
import com.chicken_road.wingames.ui.custom.MenuButton
import com.chicken_road.wingames.ui.theme.GreenBtn
import com.chicken_road.wingames.util.lockOrientation

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


