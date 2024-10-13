package com.example.core.ui.compant

import android.graphics.Typeface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun WeatherText(
    text: String,
    fontSize: TextUnit,
    fontStyle: FontStyle = FontStyle.Normal,
    fontFamily: FontFamily = FontFamily(Typeface.DEFAULT_BOLD)
) {
    Text(
        text = text,
        fontSize = fontSize,
        fontStyle = fontStyle,
        fontFamily = fontFamily,
        color = Color.White
    )
}
