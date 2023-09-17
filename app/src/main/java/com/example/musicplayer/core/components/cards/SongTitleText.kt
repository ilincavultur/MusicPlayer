package com.example.musicplayer.core.components.cards

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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

val nunitoFamily = FontFamily(
    Font(R.font.nunito_regular, FontWeight.Normal),
    Font(R.font.nunito_semibold, FontWeight.SemiBold),
    )
val valeraRound = FontFamily(
    Font(R.font.varelaround_regular, FontWeight.Normal),)

@Composable
fun SongTitleText(
    text: String,
    fontSize: TextUnit
) {
    Text(
        text = text,
        style = TextStyle(
            color = EerieBlackLight,
            fontWeight = FontWeight.ExtraBold,
            fontSize = fontSize,
            fontFamily = valeraRound
        )
    )
}