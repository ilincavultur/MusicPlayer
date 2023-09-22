package com.example.musicplayer.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.swipeable
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.musicplayer.core.components.cards.CurrentlyPlayingBar
import com.example.musicplayer.core.components.cards.SongListCard
import com.example.musicplayer.presentation.home.HomeUiEvent
import com.example.musicplayer.presentation.home.HomeViewModel
import com.example.musicplayer.ui.theme.EerieBlack
import com.example.musicplayer.ui.theme.EerieBlackLightTransparent
import com.example.musicplayer.ui.theme.PurpleAccent

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SongListScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
//    val swipeableState =
//        androidx.compose.material.rememberSwipeableState(initialValue = 1)
//    val szPx = with(LocalDensity.current) { LocalConfiguration.current.screenHeightDp.dp.toPx() }
//    val endAnchor = 0f

    //val anchors =  mapOf(0f to 0, szPx to 1)
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(color = EerieBlack)
//            .swipeable(
//                state = swipeableState,
//                anchors = anchors,
//                thresholds = { _, _ -> FractionalThreshold(0.34f) },
//                orientation = Orientation.Vertical
//            )
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
                androidx.compose.material.Scaffold(
                    bottomBar = {
                        state.currentlySelectedSong?.let {
                            if (!state.isInFullScreenMode) {
                                CurrentlyPlayingBar(
                                    Modifier,
                                    onClick = {
                                        viewModel.onEvent(HomeUiEvent.ToggleFullScreenMode)
                                    },
                                    onPlayIconClick = {
                                        viewModel.onEvent(HomeUiEvent.PlayPause)
                                    },
                                    song = it,
                                    playPauseIcon = if (state.isPlaying) {
                                        com.example.musicplayer.R.drawable.ic_baseline_pause_24
                                    } else {
                                        com.example.musicplayer.R.drawable.ic_baseline_play_arrow_24
                                    }
                                )
                            }
                        }
                    }
                ) {

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = EerieBlack)
                            .padding(it)
                    ) {
                        itemsIndexed(state.songs) { index, song ->
                            SongListCard(song, modifier = Modifier.fillMaxSize(), onSongCardClick = {
                                viewModel.onEvent(HomeUiEvent.SelectAudio(index))
                            }, isPlaying = state.isPlaying && state.currentlySelectedSong == song)
                            Divider(color = EerieBlackLightTransparent)
                        }
                    }
                }



                SongFullScreen(onClick = {
                    viewModel.onEvent(HomeUiEvent.ToggleFullScreenMode)
                },
                    //swipeableState = swipeableState
                )
            }
        }
    }
}