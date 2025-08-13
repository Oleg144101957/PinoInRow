package com.pl.nk.col.lec.ui.screens.info

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.font.FontWeight
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
    activity?.lockOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) // вертикальный режим

    Box(
        Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Background()

        Image(
            painter = painterResource(R.drawable.bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            // Заголовок с обводкой и градиентом
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = "Plnko Game",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                )
                Text(
                    text = "Plnko Game",
                    style = TextStyle(
                        brush = Gradient,
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        drawStyle = Stroke(width = 6f, join = StrokeJoin.Round)
                    )
                )
            }

            // Описание
            Text(
                text = "Place your bet and release the balls onto the board, letting gravity guide their journey. Experience the excitement with every bounce and seize your chance to collect big winnings!",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .background(Color(0x5E000000))
                    .padding(horizontal = 24.dp)
            )

            // Кнопка закрытия
            TextButton(
                text = "Close",
                modifier = Modifier.fillMaxWidth(0.6f)
            ) {
                navController.popBackStack()
            }
        }
    }
}
