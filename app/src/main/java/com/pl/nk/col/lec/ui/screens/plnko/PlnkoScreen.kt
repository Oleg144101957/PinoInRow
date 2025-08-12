package com.pl.nk.col.lec.ui.screens.plnko

import android.app.Activity
import android.content.pm.ActivityInfo
import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.pl.nk.col.lec.R
import com.pl.nk.col.lec.navigation.ScreenRoutes
import com.pl.nk.col.lec.ui.custom.Background
import com.pl.nk.col.lec.ui.custom.IconButton
import com.pl.nk.col.lec.util.lockOrientation
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.random.Random

@Composable
fun PlnkoScreen(navController: NavController, paddingValues: PaddingValues) {
    val context = LocalContext.current
    val activity = context as? Activity
    activity?.lockOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) // портрет

    Box(
        Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {
        Background()
        Column(
            Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Row(Modifier.fillMaxWidth()) {
                IconButton(iconRes = R.drawable.ic_back, modifier = Modifier.size(32.dp)) {
                    navController.popBackStack()
                }
                Spacer(Modifier.width(16.dp))
                IconButton(iconRes = R.drawable.ic_info, modifier = Modifier.size(32.dp)) {
                    navController.navigate(ScreenRoutes.SettingsScreen.route)
                }
            }
            PlinkoGame()
        }
    }
}

@Composable
fun PlinkoGame(
    viewModel: PlnkoViewModel = hiltViewModel()
) {
    val balance by viewModel.balance.collectAsState()

    val density = LocalDensity.current
    val screenWidthDp = LocalConfiguration.current.screenWidthDp.dp
    val screenWidth = with(density) { screenWidthDp.toPx() }

    val rows = 10
    val spacing = screenWidth / (rows + 2)
    val circleRadius = spacing * 0.2f
    val boardBottomY = 150f + rows * spacing + spacing

    val centerX = remember { mutableFloatStateOf(0f) }
    var ballPosition by remember { mutableStateOf(Offset(centerX.floatValue, 50f)) }
    var isDropping by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val stake = 1000 // фиксированная ставка

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Верхняя часть
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Balance: $balance", color = Color.White, fontSize = 20.sp)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    centerX.floatValue = size.width / 2
                    drawCircle(
                        color = Color.Red,
                        center = ballPosition,
                        radius = circleRadius * 1.5f
                    )
                    drawPlinkoBoard(rows, spacing, circleRadius)
                    drawMultiplierZones(boardBottomY, spacing)
                }
            }
        }

        com.pl.nk.col.lec.ui.custom.TextButton("Drop") {
            if (!isDropping && viewModel.placeStake(stake)) {
                isDropping = true
                scope.launch {
                    animateBallDrop(centerX.floatValue, boardBottomY, spacing) { newPosition ->
                        ballPosition = newPosition
                    }

                    val finalMultiplier = getMultiplier(ballPosition, centerX.floatValue, spacing)
                    val winAmount = stake * finalMultiplier
                    viewModel.addWin(winAmount)

                    isDropping = false
                }
            }
        }

    }
}


suspend fun animateBallDrop(
    startX: Float,
    boardBottomY: Float,
    spacing: Float,
    updatePosition: (Offset) -> Unit
) {
    var x = startX
    var y = 150f
    val random = Random(System.currentTimeMillis())

    val rows = 10
    val centerX = startX

    val pinPositions = (0..rows).map { row ->
        val cols = row + 1
        val rowOffset = (cols - 1) * (spacing / 2)
        List(cols) { j -> centerX - rowOffset + j * spacing }
    }

    var rowIndex = 0
    while (y < boardBottomY - spacing) {
        y += spacing

        if (rowIndex < rows) {
            val currentRow = pinPositions[rowIndex]
            val currentIndex = currentRow.indexOfFirst { abs(it - x) < spacing / 2 }

            if (currentIndex != -1) {
                // Вычисляем возможные направления движения
                val possibleMoves = mutableListOf<Int>()
                if (currentIndex > 0) possibleMoves.add(currentIndex - 1) // Влево
                if (currentIndex < currentRow.lastIndex) possibleMoves.add(currentIndex + 1) // Вправо

                if (possibleMoves.isNotEmpty()) {
                    val nextIndex = possibleMoves[random.nextInt(possibleMoves.size)]
                    x = currentRow[nextIndex]
                }
            }
            rowIndex++
        }
        updatePosition(Offset(x, y))
        delay(100)
    }
}

fun getMultiplier(ballPosition: Offset, centerX: Float, spacing: Float): Int {
    val zonePositions = List(8) { i -> centerX - (3.5f * spacing) + i * spacing }
    val zoneMultipliers = listOf(10, 7, 5, 2, 3, 5, 7, 10)

    val closestZoneIndex =
        zonePositions.indices.minByOrNull { abs(ballPosition.x - zonePositions[it]) } ?: 0
    return zoneMultipliers[closestZoneIndex]
}

fun DrawScope.drawPlinkoBoard(rows: Int, spacing: Float, circleRadius: Float) {
    val centerX = size.width / 2
    val startY = 150f

    for (i in 0 until rows) {
        val cols = i + 1
        val rowOffset = (cols - 1) * (spacing / 2)
        for (j in 0 until cols) {
            val x = centerX - rowOffset + j * spacing
            val y = startY + i * spacing
            drawCircle(color = Color.White, center = Offset(x, y), radius = circleRadius)
        }
    }
}

fun DrawScope.drawMultiplierZones(bottomY: Float, spacing: Float) {
    val rows = 10
    val centerX = size.width / 2
    val lastRowColumns = rows + 1
    val totalWidth = (lastRowColumns - 1) * spacing

    val zonePositions = List(8) { i ->
        centerX - totalWidth / 2 + (i * totalWidth / 7)
    }

    val zoneMultipliers = listOf(10, 7, 5, 2, 3, 5, 7, 10)
    val listColors = listOf(
        Color(0xFFCD0105),
        Color(0xFFCD7F01),
        Color(0xFFCD0190),
        Color(0xFF216F06),
        Color(0xFFCD0190),
        Color(0xFF216F06),
        Color(0xFFCD7F01),
        Color(0xFFCD0105),
    )

    zonePositions.forEachIndexed { index, x ->
        drawCircle(color = listColors[index], center = Offset(x, bottomY), radius = spacing * 0.5f)
        drawText("x${zoneMultipliers[index]}", Offset(x, bottomY + spacing * 0.3f))
    }
}

fun DrawScope.drawText(text: String, position: Offset) {
    drawContext.canvas.nativeCanvas.apply {
        drawText(text, position.x, position.y, Paint().apply {
            color = android.graphics.Color.WHITE
            textSize = 20f
            textAlign = Paint.Align.CENTER
        })
    }
}
