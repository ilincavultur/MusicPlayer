package com.example.musicplayer.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.musicplayer.R
import com.example.musicplayer.presentation.song_bar.CurrentlyPlayingBar
import com.example.musicplayer.core.components.cards.SongListCard
import com.example.musicplayer.presentation.home.HomeUiEvent
import com.example.musicplayer.presentation.home.HomeViewModel
import com.example.musicplayer.presentation.song.SongFullScreen
import com.example.musicplayer.ui.theme.EerieBlack
import com.example.musicplayer.ui.theme.EerieBlackLightTransparent
import com.example.musicplayer.ui.theme.PurpleAccent

@Composable
fun SongListScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(color = EerieBlack)
    ) {
        when (state.isLoading) {
            true -> {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.background(
                        color = EerieBlack
                    )
                ) {
                    CircularProgressIndicator(
                        color = PurpleAccent
                    )
                }
            }
            false -> {
                Scaffold(
                ) {

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = EerieBlack)
                            .padding(it)
                    ) {
                        itemsIndexed(state.songs) { index, song ->
                            SongListCard(song, modifier = Modifier.fillMaxSize(), onSongCardClick = { songId ->
                                viewModel.onEvent(HomeUiEvent.SelectAudio(index, songId))
                            }, isPlaying = state.isPlaying && state.currentlySelectedSong == song)
                            Divider(color = EerieBlackLightTransparent)
                        }
                    }
                }

            }
        }
    }
}