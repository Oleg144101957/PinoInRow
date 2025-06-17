package com.bi.gbass.tech.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bi.gbass.tech.domain.GameType
import com.bi.gbass.tech.ui.screens.AboutScreen
import com.bi.gbass.tech.ui.screens.HomeScreen
import com.bi.gbass.tech.ui.screens.SettingsScreen
import com.bi.gbass.tech.ui.screens.content.ContentScreen
import com.bi.gbass.tech.ui.screens.game.GameScreen
import com.bi.gbass.tech.ui.screens.splash.SplashScreen
import com.fallsview.slotttts.game.ui.screens.NoInternetScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationHost(navController: NavHostController, innerPadding: PaddingValues) {
    NavHost(navController = navController, startDestination = ScreenRoutes.SplashScreen.route) {
        composable(route = ScreenRoutes.SplashScreen.route) {
            SplashScreen(navController)
        }


        composable(route = ScreenRoutes.HomeScreen.route) {
            HomeScreen(navController, innerPadding)
        }

        composable(route = ScreenRoutes.SettingsScreen.route) {
            SettingsScreen(navController, innerPadding)
        }

        composable(route = ScreenRoutes.AboutScreen.route) {
            AboutScreen(navController, innerPadding)
        }

        composable(route = ScreenRoutes.NoNetworkScreen.route) {
            NoInternetScreen(navController, innerPadding)
        }

        composable(
            route = "${ScreenRoutes.ContentScreen.route}/{url}",
            arguments = listOf(
                navArgument("url") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val url = backStackEntry.arguments?.getString("url") ?: ""
            ContentScreen(navController, paddingValues = innerPadding, url)
        }

        composable(
            route = "${ScreenRoutes.GameScreen.route}/{gameType}",
            arguments = listOf(
                navArgument("gameType") { type = NavType.StringType },
            )
        ) {
            GameScreen(
                innerPadding,
                navController,
                it.arguments?.getString("gameType") ?: GameType.FISH.name
            )
        }
    }
}
