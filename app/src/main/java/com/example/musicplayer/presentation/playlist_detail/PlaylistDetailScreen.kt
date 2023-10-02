package com.example.musicplayer.presentation.playlist_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.musicplayer.R
import com.example.musicplayer.presentation.song_bar.CurrentlyPlayingBar
import com.example.musicplayer.core.components.cards.SongListCard
import com.example.musicplayer.core.components.dialog.SelectSongsDialog
import com.example.musicplayer.presentation.song.SongFullScreen
import com.example.musicplayer.ui.theme.EerieBlack
import com.example.musicplayer.ui.theme.EerieBlackLightTransparent
import com.example.musicplayer.ui.theme.PurpleAccent
import com.example.musicplayer.ui.theme.valeraRound

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistDetailScreen(
    navController: NavController,
    viewModel: PlaylistDetailViewModel = hiltViewModel()
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
                    topBar = {
                             TopAppBar(
                                title = {
                                    Text(
                                        text = state.playlistWithSongs.playlist.playlistName,
                                        style = TextStyle(
                                            color = Color.White,
                                            fontFamily = valeraRound,
                                            fontSize = 30.sp
                                        ),
                                        modifier = Modifier.padding(10.dp)
                                    )
                                }
                             )
                    },
                    floatingActionButton = {
                        IconButton(onClick = { viewModel.onEvent(PlaylistDetailEvent.ToggleSelectSongsDialog) }) {
                            Icon(painter = painterResource(id = R.drawable.ic_baseline_add_24), contentDescription = "add_songs")
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
                                SongListCard(song, modifier = Modifier.fillMaxSize(), onSongCardClick = { songId ->
                                    viewModel.onEvent(PlaylistDetailEvent.SelectAudio(index, songId))
                                }, isPlaying = state.isPlaying && state.currentlySelectedSong == song) //&& state.currentlySelectedSong == song
                                Divider(color = EerieBlackLightTransparent)
                            }
                        }
                    }

                    if (state.isSelectSongsDialogOpen) {
                        SelectSongsDialog(
                            songs = state.songs,
                            isSongChecked = {
                                state.checkedSongs.containsKey(it)
                            },
                            onDismissRequest = { viewModel.onEvent(PlaylistDetailEvent.ToggleSelectSongsDialog) },
                            onCancelClick = { viewModel.onEvent(PlaylistDetailEvent.OnCancelSelectSongsClick) },
                            onSongCardClick = { /*TODO*/ },
                            onCheckedChanged = { songId, checked ->
                              viewModel.onEvent(PlaylistDetailEvent.CheckSong(songId, checked))
                            },
                            onAddToPlaylistClick = {
                                viewModel.onEvent(PlaylistDetailEvent.AddToPlaylist)
                            },
                        )
                    }

                }

            }
        }
    }

}