package com.example.musicplayer.core.navigation

import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import com.example.musicplayer.R

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object SplashScreen : Screen("splash_screen", R.string.splash_screen)
    object SongListScreen : Screen("song_list_screen", R.string.song_list_screen)
}
