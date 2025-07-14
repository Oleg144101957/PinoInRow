package com.cat.cher.oma.ui.screens.game

import android.content.pm.ActivityInfo
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.cat.cher.oma.MainActivity
import com.cat.cher.oma.R
import com.cat.cher.oma.navigation.ScreenRoutes
import com.cat.cher.oma.ui.custom.Background
import com.cat.cher.oma.ui.custom.MenuButton
import com.cat.cher.oma.util.lockOrientation
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun GameScreen(
    innerPadding: PaddingValues,
    navController: NavController,
    viewModel: GameViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val activity = context as? MainActivity
    activity?.lockOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    var gameOver by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        Background()

        if (gameOver) {
            GameOverScreen(navController = navController)
        } else {
            Game(
                speed = viewModel.getSpeed(),
                elementDrawable = painterResource(id = R.drawable.candy),
                platformDrawable = painterResource(id = R.drawable.hero),
                livesPainter = painterResource(id = R.drawable.candy),
                scoreBg = painterResource(id = R.drawable.cce_btn)
            ) {
                gameOver = true
            }
        }
    }
}

@Composable
fun Game(
    speed: Float,
    elementDrawable: Painter,
    platformDrawable: Painter,
    livesPainter: Painter,
    scoreBg: Painter,
    onGameOver: () -> Unit
) {
    var score by remember { mutableIntStateOf(0) }
    var platformPosition by remember { mutableFloatStateOf(0f) }
    var lives by remember { mutableIntStateOf(3) }
    val fallingElements = remember { mutableStateListOf<FallingElement>() }

    val density = LocalDensity.current
    val configuration = LocalConfiguration.current
    val screenWidth = with(density) { configuration.screenWidthDp.dp.toPx() }
    val screenHeight = with(density) { configuration.screenHeightDp.dp.toPx() }
    val platformWidth = 100f
    val platformHeight = 100f

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            val randomX = Random.nextFloat() * (screenWidth - 80f)
            fallingElements.add(FallingElement(randomX, 0f))
        }
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(16)
            val iterator = fallingElements.iterator()
            while (iterator.hasNext()) {
                val element = iterator.next()
                val newY = element.y + speed
                if (newY > screenHeight) {
                    iterator.remove()
                    lives -= 1
                    if (lives <= 0) {
                        onGameOver()
                    }
                } else if (
                    newY + 80f >= screenHeight - platformHeight &&
                    element.x in platformPosition..(platformPosition + platformWidth)
                ) {
                    score += 5
                    iterator.remove()
                } else {
                    fallingElements[fallingElements.indexOf(element)] = element.copy(y = newY)
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectHorizontalDragGestures { _, dragAmount ->
                    platformPosition = (platformPosition + dragAmount).coerceIn(
                        0f,
                        screenWidth - platformWidth
                    )
                }
            }
    ) {
        fallingElements.forEach { element ->
            Image(
                painter = elementDrawable,
                contentDescription = "Candy",
                modifier = Modifier
                    .offset { IntOffset(element.x.toInt(), element.y.toInt()) }
                    .size(50.dp)
            )
        }

        // Platform
        Image(
            painter = platformDrawable,
            contentDescription = "Basket",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset { IntOffset(platformPosition.toInt(), 0) }
                .size(platformWidth.dp, platformHeight.dp)
        )

        // Top UI
        Row(
            Modifier.fillMaxWidth().padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = scoreBg,
                    contentDescription = "Score Background",
                    modifier = Modifier.fillMaxWidth(0.25f),
                    contentScale = ContentScale.FillWidth
                )
                Text(
                    text = "$score",
                    color = Color.White,
                    fontSize = 24.sp,
                    modifier = Modifier.align(Alignment.CenterEnd).padding(16.dp)
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(lives) {
                    Image(
                        painter = livesPainter,
                        contentDescription = "Heart",
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        }
    }
}

data class FallingElement(
    val x: Float,
    val y: Float
)

@Composable
fun GameOverScreen(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.cce_game_over),
                contentDescription = "Game Over",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                contentScale = ContentScale.FillWidth
            )

            MenuButton("Home", Modifier.padding(8.dp)) {
                navController.navigate(ScreenRoutes.HomeScreen.route)
            }
        }
    }
}



