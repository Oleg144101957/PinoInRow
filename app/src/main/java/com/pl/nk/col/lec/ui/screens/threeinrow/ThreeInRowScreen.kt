package com.pl.nk.col.lec.ui.screens.threeinrow

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pl.nk.col.lec.R
import com.pl.nk.col.lec.ui.custom.Background
import com.pl.nk.col.lec.ui.custom.MenuButton
import com.pl.nk.col.lec.ui.theme.GreenBtn
import com.pl.nk.col.lec.util.lockOrientation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThreeInRowScreen(
    viewModel: ThreeInRowViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val activity = context as? Activity
    activity?.lockOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    val board by viewModel.board.collectAsState()
    val selected by viewModel.selected.collectAsState()
    val score by viewModel.score.collectAsState()
    val bestScore by viewModel.bestScore.collectAsState()

    val moveAnimSpec = tween<IntOffset>(durationMillis = 600, easing = FastOutSlowInEasing)

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Background()
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                stringResource(R.string.score, score),
                style = MaterialTheme.typography.titleMedium.copy(color = Color.White)
            )
            Text(
                stringResource(R.string.best, bestScore),
                style = MaterialTheme.typography.titleMedium.copy(color = Color.White)
            )
            Spacer(modifier = Modifier.height(16.dp))
            board?.let { b ->
                val flatList = b.cells.flatten()
                LazyVerticalGrid(
                    columns = GridCells.Fixed(b.columns),
                    userScrollEnabled = false,
                    modifier = Modifier
                        .size((b.columns * 36).dp, (b.rows * 36).dp)
                        .background(Color(0x45000000))
                ) {
                    itemsIndexed(flatList, key = { _, gem -> gem?.id ?: -1L }) { index, gem ->
                        val row = index / b.columns
                        val col = index % b.columns
                        val type = gem?.type
                        val isSelected = selected == row to col
                        Modifier
                            .size(36.dp)
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(if (type == null) Color.LightGray else Color.Transparent)
                            .border(
                                width = if (isSelected) 3.dp else 1.dp,
                                color = if (isSelected) Color.Black else Color.Gray,
                                shape = CircleShape
                            )
                        Box(
                            modifier = Modifier
                                .animateItem(
                                    fadeInSpec = null,
                                    fadeOutSpec = null,
                                    placementSpec = moveAnimSpec
                                )
                                .clickable(enabled = type != null) {
                                    viewModel.onCellClick(row, col)
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            AnimatedContent(
                                targetState = type,
                                transitionSpec = {
                                    fadeIn(animationSpec = tween(220)) + scaleIn(
                                        initialScale = 0.7f,
                                        animationSpec = tween(220)
                                    ) togetherWith
                                            fadeOut(animationSpec = tween(220))
                                }
                            ) { animType ->
                                if (animType != null) {
                                    Image(
                                        painter = painterResource(id = animType.imageRes),
                                        contentDescription = null,
                                        modifier = Modifier.size(32.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            MenuButton("New Game", modifier = Modifier) {
                viewModel.newGame()
            }
        }
    }
}
