package com.example.musicplayer.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColorScheme (
    primary = PurpleAccent,
    secondary = PurpleAccent,
    background = EerieBlack,
    surface = PurpleAccent,
    onBackground = PurpleAccent

)

@SuppressLint("ConflictingOnColor")
private val LightColorPalette = lightColorScheme (
    primary = PurpleAccent,
    //primaryVariant = PurpleAccent,
    secondary = PurpleAccent,
    background = EerieBlack,
    surface = EerieBlack,
    onSurface = PurpleAccent,
    onBackground = PurpleAccent,
    onPrimary = PurpleAccent,


    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun MusicPlayerTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = Shapes(

        ),
        content = content
    )
}