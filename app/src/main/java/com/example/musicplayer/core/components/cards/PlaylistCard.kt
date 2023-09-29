package com.example.musicplayer.core.components.cards

import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.musicplayer.R
import com.example.musicplayer.ui.theme.EerieBlack
import com.example.musicplayer.ui.theme.EerieBlackLight
import com.example.musicplayer.ui.theme.EerieBlackMedium

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlaylistCard(
    text: String,
    playlistId: Int,
    onCardClick: (Int) -> Unit,
    onCardLongClick: () -> Unit,
    selectedImageUri: Uri?
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height = 150.dp)
            .background(color = EerieBlackMedium, shape = RoundedCornerShape(size = 4.dp))
            .combinedClickable(
                onClick = { onCardClick(playlistId) },
                onLongClick = onCardLongClick,
                onLongClickLabel = stringResource(R.string.open_context_menu)
            )
        ,
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = selectedImageUri,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Text(
            text = text,
            style = TextStyle(
                color = White
            )
        )
    }
}