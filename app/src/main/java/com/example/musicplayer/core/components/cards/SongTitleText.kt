package com.example.musicplayer.core.components.cards

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.musicplayer.R
import com.example.musicplayer.ui.theme.EerieBlackLight
import com.example.musicplayer.ui.theme.valeraRound


@Composable
fun SongTitleText(
    text: String,
    fontSize: TextUnit,
    color: Color
) {
    Text(
        text = text,
        style = TextStyle(
            color = color,
            fontWeight = FontWeight.ExtraBold,
            fontSize = fontSize,
            fontFamily = valeraRound
        )
    )
}