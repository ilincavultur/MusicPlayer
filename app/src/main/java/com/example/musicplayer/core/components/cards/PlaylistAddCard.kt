package com.example.musicplayer.core.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign


import androidx.compose.ui.unit.dp
import com.example.musicplayer.ui.theme.EerieBlack
import com.example.musicplayer.ui.theme.EerieBlackLight
import com.example.musicplayer.ui.theme.EerieBlackMedium


import java.nio.file.Files.size

@Composable
fun PlaylistAddCard(
    createPlaylist: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height = 150.dp)
            .background(color = EerieBlackMedium, shape = RoundedCornerShape(size = 4.dp)),
        contentAlignment = Alignment.Center
    ) {
        IconButton(onClick = createPlaylist) {
            Icon(
                painter = painterResource(id = com.example.musicplayer.R.drawable.ic_baseline_add_24),
                contentDescription = "create playlist",
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center),
                tint = EerieBlackLight
            )
        }
    }

}