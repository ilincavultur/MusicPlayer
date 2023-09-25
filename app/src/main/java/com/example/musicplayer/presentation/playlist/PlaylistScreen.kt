package com.example.musicplayer.presentation.playlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.musicplayer.core.components.cards.PlaylistAddCard
import com.example.musicplayer.core.components.cards.PlaylistCard

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
                    viewModel.onEvent(PlaylistScreenEvent.CreatePlaylist("blabla"))
                }
            )
        }
    }
}