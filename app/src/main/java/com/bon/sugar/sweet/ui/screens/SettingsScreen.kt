package com.bon.sugar.sweet.ui.screens

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bon.sugar.sweet.R
import com.bon.sugar.sweet.navigation.ScreenRoutes
import com.bon.sugar.sweet.ui.custom.Background
import com.bon.sugar.sweet.ui.custom.MenuButton
import com.bon.sugar.sweet.ui.theme.DefFont
import com.bon.sugar.sweet.util.CustomTabsUtil
import com.bon.sugar.sweet.util.lockOrientation

@Composable
fun SettingsScreen(navController: NavController, paddingValues: PaddingValues) {
    val context = LocalContext.current
    val activity = context as? Activity
    activity?.lockOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    BackHandler {}
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Background()
        Column(
            Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                MenuButton(R.drawable.ic_back, modifier = Modifier.size(50.dp)) {
                    navController.popBackStack()
                }
                Text(
                    text = "Settings",
                    style = TextStyle(
                        color = White,
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontFamily = DefFont
                    )
                )
                Box(modifier = Modifier.size(40.dp))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                MenuButton(
                    "Privacy Policy",
                    modifier = Modifier.fillMaxWidth(0.8f)
                ) {
                    CustomTabsUtil.openCustomTab(
                        context,
                        "https://sites.google.com/view/wdgsvtkn-06-20/wdGSVTkn-06-20"
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                MenuButton(
                    "About Us",
                    modifier = Modifier.fillMaxWidth(0.8f)
                ) {
                    navController.navigate(ScreenRoutes.AboutScreen.route)
                }
            }
        }
    }
}
