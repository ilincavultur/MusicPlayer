package com.example.musicplayer.core.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.musicplayer.presentation.SongFullScreen
import com.example.musicplayer.presentation.SongListScreen
import com.example.musicplayer.presentation.SplashScreen

@Composable
fun Navigation(
    navController: NavHostController,
    innerPadding: PaddingValues,
    snackbarHostState: SnackbarHostState
) {
    NavHost(navController = navController, startDestination = Screen.SplashScreen.route, Modifier.padding(innerPadding)) {
        composable(route = Screen.SplashScreen.route) {
            SplashScreen(navController = navController)
        }

        composable(route = Screen.SongListScreen.route) {
            SongListScreen(navController = navController)
        }

//        composable(route = Screen.PlaySongScreen.route) {
//            SongFullScreen(navController = navController, onClick = { dest -> navController.navigate(dest) })
//        }

    }
}