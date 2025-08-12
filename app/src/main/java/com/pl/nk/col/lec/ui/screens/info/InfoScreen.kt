package com.pl.nk.col.lec.ui.screens.info

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.pl.nk.col.lec.R
import com.pl.nk.col.lec.ui.custom.Background
import com.pl.nk.col.lec.ui.custom.TextButton
import com.pl.nk.col.lec.ui.theme.Gradient
import com.pl.nk.col.lec.util.lockOrientation

@Composable
fun InfoScreen(navController: NavController, paddingValues: PaddingValues) {

    val context = LocalContext.current
    val activity = context as? Activity
    activity?.lockOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)

    Box(
        Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Background()
        Image(
            painterResource(R.drawable.bg),
            contentDescription = R.drawable.bg.toString(),
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        Column(
            Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Box {
                Text(
                    "Plnko Game",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 64.sp
                    )
                )
                Text(
                    "Plnko Game",
                    style = TextStyle(
                        brush = Gradient,
                        fontSize = 64.sp,
                        drawStyle = Stroke(
                            width = 6f, join = StrokeJoin.Round
                        )
                    )
                )
            }
            Text(
                "Place your bet and release the balls onto the board, letting gravity guide their journey. Experience the excitement with every bounce and seize your chance to collect big winnings!",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.padding(horizontal = 32.dp)
            )
            TextButton("Close", modifier = Modifier.fillMaxWidth(0.5f)) {
                navController.popBackStack()
            }
        }
    }
}
