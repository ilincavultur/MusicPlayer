package com.example.musicplayer.core.components.cards


import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import com.example.musicplayer.ui.theme.valeraRound


@Composable
fun SongArtistText(
    text: String,
    fontSize: TextUnit,
    color: Color
) {
    Text(
        text = text,
        style = TextStyle(
            color = color,
            fontWeight = FontWeight.Bold,
            fontSize = fontSize,
            fontFamily = valeraRound
        )
    )
}