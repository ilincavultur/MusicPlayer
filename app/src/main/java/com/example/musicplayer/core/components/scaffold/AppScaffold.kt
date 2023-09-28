package com.example.musicplayer.core.components.scaffold

import android.content.Context
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.musicplayer.core.components.bottom.HomeBottomBar
import com.example.musicplayer.core.components.cards.CurrentlyPlayingBar
import com.example.musicplayer.core.navigation.Navigation
import com.example.musicplayer.core.navigation.Screen
import com.example.musicplayer.ui.theme.EerieBlack


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(

) {
    val navController = rememberNavController()
    //val scaffoldState = rememberScaffoldState()
    //val snackbarHost =
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
        },
        //scaffoldState = scaffoldState,
    ) { innerPadding ->
        Navigation(navController = navController, innerPadding = innerPadding)
    }
}