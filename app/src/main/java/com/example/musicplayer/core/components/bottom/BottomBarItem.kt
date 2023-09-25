package com.example.musicplayer.core.components.bottom

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector


data class BottomBarItem(
    val route: String,
    val icon: ImageVector,
    val resourceId: Int
)