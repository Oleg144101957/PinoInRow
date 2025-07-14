package com.boo.me.ran.g.ui.custom

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.boo.me.ran.g.R

@Composable
fun MenuButton(imageRes: Int, modifier: Modifier, onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth(0.5f)
            .clickable { onClick() }
    ) {
        Image(
            painterResource(imageRes), imageRes.toString(),
            modifier = Modifier.fillMaxWidth(), contentScale = ContentScale.FillWidth
        )
    }
}

@Composable
fun MenuButton(name: String, modifier: Modifier, onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth(0.5f)
            .clickable { onClick() }
    ) {
        Image(
            painterResource(R.drawable.cce_btn), R.drawable.cce_btn.toString(),
            modifier = Modifier.fillMaxWidth(), contentScale = ContentScale.FillWidth
        )
        Text(
            text = name,
            fontSize = 22.sp,
            fontWeight = FontWeight.Black,
            color = Color.White
        )
    }
}
