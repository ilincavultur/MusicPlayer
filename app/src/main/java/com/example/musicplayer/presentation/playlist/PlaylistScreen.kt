package com.example.musicplayer.presentation.playlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.musicplayer.core.components.cards.PlaylistAddCard
import com.example.musicplayer.core.components.cards.PlaylistCard
import com.example.musicplayer.core.components.dialog.PlaylistDialog
import com.example.musicplayer.ui.theme.PurpleAccent

@Composable
fun PlaylistScreen(
    navController: NavController,
    viewModel: PlaylistScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Adaptive(minSize = 150.dp), // adaptive size
        verticalArrangement = Arrangement.spacedBy(space = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(space = 16.dp),
        contentPadding = PaddingValues(all = 10.dp)
    ) {
        itemsIndexed(state.playlists) { index, playlist ->
            PlaylistCard(text = playlist.playlist.playlistName)
        }
        items(count = 1) {
            PlaylistAddCard(
                createPlaylist = {
                    viewModel.onEvent(PlaylistScreenEvent.ToggleCreateDialog)
                }
            )
        }
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