package com.example.musicplayer.core.components.scaffold

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.musicplayer.R
import com.example.musicplayer.core.components.bottom.HomeBottomBar
import com.example.musicplayer.core.navigation.Navigation
import com.example.musicplayer.core.navigation.Screen
import com.example.musicplayer.presentation.home.HomeUiEvent
import com.example.musicplayer.presentation.song.SongFullScreen
import com.example.musicplayer.presentation.song_bar.CurrentlyPlayingBar
import com.example.musicplayer.ui.theme.EerieBlack


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    Scaffold(
        contentColor = EerieBlack,
        containerColor = EerieBlack,
        snackbarHost = {
            //SnackbarHost(hostState = snackbarHost.snackbarHostState)
        },
        topBar = {
            if (navBackStackEntry?.destination?.route !in listOf(
                    Screen.SplashScreen.route,
            )) {
                TopAppBar(
                    modifier = Modifier.height(45.dp),
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = EerieBlack,
                    ),
                    title = {

                    }
                )
            }
        },
        bottomBar = {
            if (navBackStackEntry?.destination?.route !in listOf(
                    Screen.SplashScreen.route,
                )) {
                Column {
                    CurrentlyPlayingBar(
                        Modifier
                    )
                    HomeBottomBar(
                        currentDestination = navBackStackEntry?.destination?.route,
                        onClick = { route ->
                            navController.navigate(route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        },
    ) { innerPadding ->
        Navigation(navController = navController, innerPadding = innerPadding)
    }
}