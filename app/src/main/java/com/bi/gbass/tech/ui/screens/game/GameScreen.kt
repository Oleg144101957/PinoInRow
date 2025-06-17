package com.bi.gbass.tech.ui.screens.game

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bi.gbass.tech.MainActivity
import com.bi.gbass.tech.R
import com.bi.gbass.tech.domain.GameType
import com.bi.gbass.tech.navigation.ScreenRoutes
import com.bi.gbass.tech.ui.custom.Background
import com.bi.gbass.tech.ui.custom.MenuButton
import com.bi.gbass.tech.util.lockOrientation
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun GameScreen(
    innerPadding: PaddingValues,
    navController: NavController,
    gameType: String,
    viewModel: GameViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val activity = context as? MainActivity
    activity?.lockOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    var gameOver by remember { mutableStateOf(false) }
    val game = GameType.valueOf(gameType)

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
                viewModel.getSpeed(),
                when (game) {
                    GameType.FISH -> listOf(painterResource(id = R.drawable.fish))
                    GameType.SHRIMP -> listOf(painterResource(id = R.drawable.shrimp))
                },
                when (game) {
                    GameType.FISH -> painterResource(id = R.drawable.hero)
                    GameType.SHRIMP -> painterResource(id = R.drawable.hero)
                },
                when (game) {
                    GameType.FISH -> painterResource(R.drawable.fish)
                    GameType.SHRIMP -> painterResource(R.drawable.shrimp)
                },
                when (game) {
                    GameType.FISH -> painterResource(R.drawable.cce_btn)
                    GameType.SHRIMP -> painterResource(R.drawable.cce_btn)
                }
            ) {
                gameOver = true
            }
        }
    }
}


@Composable
fun Game(
    speed: Float,
    elementDrawables: List<Painter>,
    platformDrawable: Painter,
    livesPainter: Painter,
    scoreBg: Painter,
    onGameOver: () -> Unit
) {
    var score by remember { mutableIntStateOf(0) }
    var platformPosition by remember { mutableFloatStateOf(0f) }
    var lives by remember { mutableIntStateOf(3) }  // Track lives
    val fallingElements = remember { mutableStateListOf<FallingElement>() }

    val screenWidth = with(LocalDensity.current) { LocalConfiguration.current.screenWidthDp.dp }
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val platformHeight = 100.dp
    val platformWidth = 100.dp

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            val randomX = Random.nextFloat() * (screenWidth.value - 80)
            val randomDrawable = elementDrawables.random()
            fallingElements.add(FallingElement(randomX, 0f, randomDrawable))
        }
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(16)
            val iterator = fallingElements.iterator()
            while (iterator.hasNext()) {
                val element = iterator.next()
                val newY = element.y + speed
                if (newY > screenHeight.value) {
                    iterator.remove()
                    if (newY + 80 >= screenHeight.value - platformHeight.value) {
                        lives -= 1
                    }
                    if (lives <= 0) {
                        onGameOver()
                    }
                } else if (
                    newY + 80 >= screenHeight.value - platformHeight.value &&
                    element.x >= platformPosition &&
                    element.x <= platformPosition + platformWidth.value
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
                        screenWidth.value - platformWidth.value
                    )
                }
            }
    ) {
        fallingElements.forEach { element ->
            Image(
                painter = element.drawable,
                contentDescription = "Falling Element",
                modifier = Modifier
                    .offset(element.x.dp, element.y.dp)
                    .size(50.dp)
            )
        }
        // Platform
        Image(
            painter = platformDrawable,
            contentDescription = "Platform",
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(x = platformPosition.dp)
                .size(platformWidth, platformHeight)
        )
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Image(
                    scoreBg,
                    scoreBg.toString(),
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
                // Display "Game Over" message if lives are 0
                if (lives <= 0) {
                    Text(
                        text = "Game Over!",
                        color = Color.White,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(lives) {
                    Image(
                        painter = livesPainter,
                        contentDescription = livesPainter.toString(),
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

data class FallingElement(
    val x: Float,
    val y: Float,
    val drawable: Painter
)



