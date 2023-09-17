package com.example.musicplayer.core.components.cards

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.example.musicplayer.ui.theme.EerieBlackLight

@Composable
fun SongArtistText(
    text: String,
    fontSize: TextUnit
) {
    Text(
        text = text,
        style = TextStyle(
            color = EerieBlackLight,
            fontWeight = FontWeight.Bold,
            fontSize = fontSize,
            fontFamily = valeraRound
        )
    )
}