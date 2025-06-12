package com.chicken_road.wingames.ui.screens.game

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.chicken_road.wingames.R
import com.chicken_road.wingames.domain.GameType
import com.chicken_road.wingames.navigation.ScreenRoutes
import com.chicken_road.wingames.ui.custom.Background
import com.chicken_road.wingames.ui.custom.MenuButton
import com.chicken_road.wingames.util.lockOrientation
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun GameScreen(
    navController: NavController,
    gameType: String,
    viewModel: GameViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val activity = context as? Activity
    activity?.lockOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    var gameOver by remember { mutableStateOf(false) }
    val game = GameType.valueOf(gameType.uppercase())

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding()
    ) {
        Background(R.drawable.bg_game)

        if (gameOver) {
            GameOverScreen(navController = navController)
        } else {
            val elementDrawable = when (game) {
                GameType.FISH -> painterResource(id = R.drawable.fish)
                GameType.SHRIMP -> painterResource(id = R.drawable.shrimp)
            }

            val heroDrawable = painterResource(id = R.drawable.hero)

            Game(
                speed = viewModel.getSpeed(),
                elementDrawable = elementDrawable,
                platformDrawable = heroDrawable,
                livesPainter = elementDrawable,
                scoreBg = painterResource(R.drawable.cce_btn)
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

    val screenWidth = with(LocalDensity.current) { LocalConfiguration.current.screenWidthDp.dp }
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val platformHeight = 100.dp
    val platformWidth = 100.dp

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            val randomX = Random.nextFloat() * (screenWidth.value - 80)
            fallingElements.add(FallingElement(randomX, 0f))
        }
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(16)
            val iterator = fallingElements.iterator()
            while (iterator.hasNext()) {
                val catchZone = 10
                val element = iterator.next()
                val newY = element.y + speed
                if (newY > screenHeight.value) {
                    iterator.remove()
                    if (newY + 50 >= screenHeight.value - platformHeight.value) {
                        lives -= 1
                    }
                    if (lives <= 0) {
                        onGameOver()
                    }
                } else

                    if (
                        newY + 50 >= screenHeight.value - platformHeight.value - catchZone &&
                        newY + 50 <= screenHeight.value - platformHeight.value + catchZone &&
                        element.x + 50 >= platformPosition &&
                        element.x <= platformPosition + platformWidth.value
                    ) {
                        score += 5
                        iterator.remove()
                    } else {
                        element.y = newY
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
                        screenWidth.value - platformWidth.value
                    )
                }
            }
    ) {
        fallingElements.forEach { element ->
            Image(
                painter = elementDrawable,
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .offset(element.x.dp, element.y.dp)
                    .size(50.dp)
            )
        }

        Image(
            painter = platformDrawable,
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(x = platformPosition.dp)
                .size(platformWidth, platformHeight)
        )

        // Панель очков и жизней
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(contentAlignment = Alignment.Center) {
                Image(
                    scoreBg,
                    null,
                    modifier = Modifier.fillMaxWidth(0.25f),
                    contentScale = ContentScale.FillWidth
                )
                Text(
                    text = "$score",
                    color = Color.White,
                    fontSize = 24.sp,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(16.dp)
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(lives) {
                    Image(
                        painter = livesPainter,
                        contentDescription = null,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun GameOverScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.cce_game_over),
                contentDescription = R.drawable.cce_game_over.toString(),
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


class FallingElement(
    val x: Float,
    var y: Float
)



