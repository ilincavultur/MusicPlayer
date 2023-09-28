package com.example.musicplayer.presentation.song_bar

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.musicplayer.R
import com.example.musicplayer.core.components.cards.SongArtistText
import com.example.musicplayer.core.components.cards.SongTitleText
import com.example.musicplayer.core.components.scaffold.AppScaffold
import com.example.musicplayer.domain.models.Song
import com.example.musicplayer.presentation.song.SongFullScreen
import com.example.musicplayer.ui.theme.EerieBlackLight
import com.example.musicplayer.ui.theme.EerieBlackMedium


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CurrentlyPlayingBar(
    modifier: Modifier,
    viewModel: SongBarViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    if (state.isInFullScreenMode) {
        SongFullScreen(
            onClick = {
                viewModel.onEvent(SongBarEvent.ToggleFullScreenMode)
            },
        )
    }

    if (!state.isInFullScreenMode && state.currentlySelectedSong != null) {
        Box(
            modifier = modifier
                .background(color = EerieBlackMedium)
                .fillMaxWidth()
                .height(70.dp)
                .padding(10.dp)
                .clickable {
                    viewModel.onEvent(SongBarEvent.ToggleFullScreenMode)
                },
        ) {

            Row(
                modifier = modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = state.currentlySelectedSong?.coverUrl.toString(),
                            contentDescription = "image_preview",
                            modifier = Modifier
                                .size(65.dp)
                                .clip(RoundedCornerShape(10.dp))
                        )
                        Spacer(modifier = Modifier.size(10.dp))
                        Column(
                            modifier = Modifier
                                .fillMaxHeight(),
                            verticalArrangement = Arrangement.Center
                        ) {
                            SongTitleText(
                                text = state.currentlySelectedSong?.title.toString(),
                                fontSize = 20.sp,
                                color = EerieBlackLight
                            )
                            SongArtistText(
                                text = state.currentlySelectedSong?.artist.toString(),
                                fontSize = 15.sp,
                                color = EerieBlackLight
                            )
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ) {
                    IconButton(onClick = {
                        viewModel.onEvent(SongBarEvent.PlayPause)
                        //onPlayIconClick()
                    }) {
                        Icon(
                            painter = painterResource(
                                id = if (state.isPlaying) {
                                    R.drawable.ic_baseline_pause_24
                                } else {
                                    R.drawable.ic_baseline_play_arrow_24
                                }
                            ),
                            contentDescription = "play_icon",
                            tint = EerieBlackLight,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }
        }
    }

}