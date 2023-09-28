package com.example.musicplayer.presentation.playlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.musicplayer.core.components.cards.PlaylistActionsSheet
import com.example.musicplayer.core.components.cards.PlaylistAddCard
import com.example.musicplayer.core.components.cards.PlaylistCard
import com.example.musicplayer.core.components.dialog.PlaylistDialog
import kotlinx.coroutines.launch


@Composable
fun PlaylistScreen(
    navController: NavController,
    viewModel: PlaylistScreenViewModel = hiltViewModel(),
    onNavigateToPlaylistDetails: (Int) -> Unit,
) {
    val state = viewModel.state.value
    val haptics = LocalHapticFeedback.current
    val coroutineScope = rememberCoroutineScope()

    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Adaptive(minSize = 150.dp), // adaptive size
        verticalArrangement = Arrangement.spacedBy(space = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(space = 16.dp),
        contentPadding = PaddingValues(all = 10.dp)
    ) {
        itemsIndexed(state.playlists) { index, playlist ->
            PlaylistCard(
                text = playlist.playlist.playlistName,
                playlistId = playlist.playlist.playlistId ?: -1,
                onCardClick = { playlistId ->
                    println("playlistId:  " + playlist.playlist.playlistId)
                    onNavigateToPlaylistDetails(playlistId)
                },
                onCardLongClick = {
                    haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                    viewModel.onEvent(PlaylistScreenEvent.OnCardLongClick(playlist.playlist.playlistId ?: -1))
                }
            )
        }
        items(count = 1) {
            PlaylistAddCard(
                createPlaylist = {
                    viewModel.onEvent(PlaylistScreenEvent.ToggleCreateDialog)
                }
            )
        }
    }


    if (state.contextMenuPlaylistId != null && state.contextMenuPlaylistId != -1) {
        PlaylistActionsSheet(
            playlistWithSongs = state.playlists.first { it.playlist.playlistId == state.contextMenuPlaylistId },
            onDeleteButtonClick = {
                viewModel.onEvent(PlaylistScreenEvent.DeletePlaylist)
            },
            onDismissSheet = { viewModel.onEvent(PlaylistScreenEvent.OnDismissSheet) },
            coroutineScope = coroutineScope
        )
    }

    if (state.isCreateDialogOpen) {
        PlaylistDialog(
            onDismissRequest = { viewModel.onEvent(PlaylistScreenEvent.ToggleCreateDialog) },
            dialogTitle = "Choose a playlist name",
            dialogText = state.dialogText,
            updateText = {
                viewModel.onEvent(PlaylistScreenEvent.UpdateDialogText(it))
            },
        )
    }

}