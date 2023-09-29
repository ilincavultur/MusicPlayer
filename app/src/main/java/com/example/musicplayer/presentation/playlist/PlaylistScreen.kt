package com.example.musicplayer.presentation.playlist

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
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
    val context = LocalContext.current

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            println("selectedImageUri" + uri.toString())
            val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
            if (uri != null) {
                context.contentResolver.takePersistableUriPermission(uri, flag)
                viewModel.onEvent(PlaylistScreenEvent.SetCoverPhoto(context, uri.toString()))
            }

        }
    )

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
                },
                selectedImageUri = playlist.playlist.playlistCoverPhoto.toUri()
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
            coroutineScope = coroutineScope,
            onChooseCoverPhotoClick = {
                singlePhotoPickerLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            }
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