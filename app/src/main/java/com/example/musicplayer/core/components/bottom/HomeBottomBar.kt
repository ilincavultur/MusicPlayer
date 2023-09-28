package com.example.musicplayer.core.components.bottom

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.PlaylistAdd
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import com.example.musicplayer.core.navigation.Screen
import com.example.musicplayer.ui.theme.EerieBlack
import com.example.musicplayer.ui.theme.EerieBlackMedium
import com.example.musicplayer.ui.theme.PurpleAccent

@Composable
fun HomeBottomBar(
    currentDestination: String?,
    onClick: (String) -> Unit
) {
    val items : List<BottomBarItem> = listOf(
        BottomBarItem(
            route = Screen.SongListScreen.route,
            icon = Icons.Default.Home,
            resourceId = Screen.SongListScreen.resourceId
        ),
        BottomBarItem(
            route = Screen.PlaylistScreen.route,
            icon = Icons.Default.PlaylistAdd,
            resourceId = Screen.PlaylistScreen.resourceId
        ),
    )
    BottomAppBar(
        containerColor = EerieBlack,
        contentColor = PurpleAccent,
        modifier = Modifier.height(70.dp)
    ) {

        items.forEach { screen ->
            val isSelected = currentDestination == screen.route

            val lineLength = animateFloatAsState(
                targetValue = if(isSelected) 1f else 0f,
                animationSpec = tween(
                    durationMillis = 300
                )
            )

            NavigationBarItem(
                icon = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp)
                            .drawBehind {
                                if (lineLength.value > 0f) {
                                    drawLine(
                                        color = if (isSelected) Color.White
                                        else Color.White,
                                        start = Offset(
                                            size.width / 2f - lineLength.value * 15.dp.toPx(),
                                            size.height
                                        ),
                                        end = Offset(
                                            size.width / 2f + lineLength.value * 15.dp.toPx(),
                                            size.height
                                        ),
                                        strokeWidth = 2.dp.toPx(),
                                        cap = StrokeCap.Round
                                    )
                                }
                            }
                    ) {
                        Icon(screen.icon, contentDescription = null, modifier = Modifier.align(
                            Alignment.Center))
                    }
                },
                selected = isSelected,
                onClick = { onClick(screen.route) },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = EerieBlack,
                    selectedIconColor = PurpleAccent
                )
            )
        }

    }
}