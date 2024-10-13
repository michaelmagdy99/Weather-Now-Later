package com.example.core.ui.compant

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter

@Composable
fun LoadingImage(
    imageUrl: String?,
    modifier: Modifier = Modifier,
    contentDescription: String = "Image",
    size: Dp = 210.dp
) {
    val painter = rememberImagePainter(data = imageUrl,)
    Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier
            .fillMaxWidth()
            .size(size)
    )
}
