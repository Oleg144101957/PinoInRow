package com.bon.sugar.sweet.ui.custom

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.bon.sugar.sweet.R

@Composable
fun Background(imageRes: Int = R.drawable.bg_game) {
    Image(
        painter = painterResource(id = imageRes),
        contentDescription = imageRes.toString(),
        contentScale = ContentScale.FillBounds,
        modifier = Modifier.fillMaxSize()
    )
}
