package com.example.musicplayer.core.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.musicplayer.presentation.SongListScreen
import com.example.musicplayer.presentation.SplashScreen
import com.example.musicplayer.presentation.playlist.PlaylistScreen
import com.example.musicplayer.presentation.playlist_detail.PlaylistDetailScreen

@Composable
fun Navigation(
    navController: NavHostController,
    innerPadding: PaddingValues,
    //snackbarHostState: SnackbarHostState
) {
    NavHost(navController = navController, startDestination = Screen.SplashScreen.route, Modifier.padding(innerPadding)) {
        composable(route = Screen.SplashScreen.route) {
            SplashScreen(navController = navController)
        }

        composable(route = Screen.SongListScreen.route) {
            SongListScreen(navController = navController)
        }

        composable(route = Screen.PlaylistScreen.route) {
            PlaylistScreen(navController = navController, onNavigateToPlaylistDetails = {
                navController.navigate(Screen.PlaylistDetailScreen.withArgs(it)) {
                    navOptions {
                        navArgument("playlistWithSongsId") {
                            type = NavType.IntType
                            nullable = false
                        }
                    }
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                }
            })
        }

        composable(
            route = Screen.PlaylistDetailScreen.route + "/{playlistWithSongsId}",
            arguments = listOf(
                navArgument("playlistWithSongsId") {
                    type = NavType.IntType
                    nullable = false
                }
            )
        ) {
            PlaylistDetailScreen(navController = navController)
        }

    }
}