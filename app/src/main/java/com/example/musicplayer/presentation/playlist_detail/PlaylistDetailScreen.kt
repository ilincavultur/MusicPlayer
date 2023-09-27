package com.example.musicplayer.presentation.playlist_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.musicplayer.R
import com.example.musicplayer.core.components.cards.CurrentlyPlayingBar
import com.example.musicplayer.core.components.cards.SongListCard
import com.example.musicplayer.presentation.SongFullScreen
import com.example.musicplayer.presentation.home.HomeUiEvent
import com.example.musicplayer.ui.theme.EerieBlack
import com.example.musicplayer.ui.theme.EerieBlackLightTransparent
import com.example.musicplayer.ui.theme.PurpleAccent

@Composable
fun PlaylistDetailScreen(
    navController: NavController,
    viewModel: PlaylistDetailViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    //viewModel.filteredData.collectAsStateWithLifecycle()

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
                    topBar = {
                             TopAppBar {
//                                 filteredData.let {
//                                     Text(text = it.value.playlist.playlistName, style = TextStyle(
//                                         color = Color.White
//                                     ))
//                                 }
                                 Text(text = state.playlistWithSongs.playlist.playlistName, style = TextStyle(
                                     color = Color.White
                                 ))
                             }
                    },
                    bottomBar = {
                        state.currentlySelectedSong?.let {
                            if (!state.isInFullScreenMode) {
                                CurrentlyPlayingBar(
                                    Modifier,
                                    onClick = {
                                        viewModel.onEvent(PlaylistDetailEvent.ToggleFullScreenMode)
                                    },
                                    onPlayIconClick = {
                                        viewModel.onEvent(PlaylistDetailEvent.PlayPause)
                                    },
                                    song = it,
                                    playPauseIcon = if (state.isPlaying) {
                                        R.drawable.ic_baseline_pause_24
                                    } else {
                                        R.drawable.ic_baseline_play_arrow_24
                                    }
                                )
                            }
                        }
                    }
                ) { paddingValues ->

                    state.playlistWithSongs.let { playlist ->
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = EerieBlack)
                                .padding(paddingValues)
                        ) {
                            itemsIndexed(playlist.songs) { index, song ->
                                SongListCard(song, modifier = Modifier.fillMaxSize(), onSongCardClick = {
                                    viewModel.onEvent(PlaylistDetailEvent.SelectAudio(index))
                                }, isPlaying = state.isPlaying && state.currentlySelectedSong == song)
                                Divider(color = EerieBlackLightTransparent)
                            }
                        }
                    }
                }

                SongFullScreen(onClick = {
                    viewModel.onEvent(PlaylistDetailEvent.ToggleFullScreenMode)
                },
                )
            }
        }
    }

}