package com.example.musicplayer.presentation.song

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.musicplayer.R
import com.example.musicplayer.core.components.cards.SongArtistText
import com.example.musicplayer.core.components.cards.SongTitleText
import com.example.musicplayer.presentation.home.HomeState
import com.example.musicplayer.presentation.home.HomeUiEvent
import com.example.musicplayer.presentation.home.HomeViewModel
import com.example.musicplayer.presentation.song_bar.SongBarEvent
import com.example.musicplayer.presentation.song_bar.SongBarState
import com.example.musicplayer.presentation.song_bar.SongBarViewModel
import com.example.musicplayer.ui.theme.EerieBlack
import com.example.musicplayer.ui.theme.EerieBlackLight


@Composable
fun SongFullScreen(
    viewModel: SongBarViewModel = hiltViewModel(),
    onClick: () -> Unit,
) {
    val state = viewModel.state.value
    val currentlySelectedSong = state.currentlySelectedSong
    val context = LocalContext.current

    var localSliderValue by remember { mutableStateOf(0f) }
    val animationTime = 300

    AnimatedVisibility(
        visible = state.isInFullScreenMode && (state.currentlySelectedSong != null),
        enter = slideInVertically(
            initialOffsetY = { it },
            animationSpec = tween(
                durationMillis = animationTime,
                easing = LinearEasing
            )
        ),
        exit = slideOutVertically  (
            targetOffsetY = {
                it
            },
            animationSpec = tween(
                durationMillis = animationTime,
                easing = LinearEasing
            )
        ),
    ) {
           Column(
               modifier = Modifier.fillMaxSize(),
                   //.padding(top = 35.dp),
               verticalArrangement = Arrangement.Center

           ) {

               Row(
                   verticalAlignment = Alignment.Top,
                   modifier = Modifier
                       .background(color = EerieBlack)
                       .fillMaxWidth()
                       .padding(10.dp)
                       .height(20.dp).
                           clickable {
                               onClick()
                           }
               ) {
                   IconButton(onClick = {
                       onClick()
                   }) {
                       Icon(painter = painterResource(id = R.drawable.ic_baseline_keyboard_arrow_down_24), contentDescription = "minimize_current_song", tint = EerieBlackLight)
                   }

               }

               Row(
                   verticalAlignment = Alignment.Top,
                   modifier = Modifier
                       .background(color = EerieBlack)
                       .fillMaxWidth()
                       .height(410.dp)
               ) {
                   SongCoverPreview(
                       coverUrl = currentlySelectedSong?.coverUrl.toString()
                   )
               }

               Row(
                   verticalAlignment = Alignment.Top,
                   horizontalArrangement = Arrangement.End,
                   modifier = Modifier
                       .background(color = EerieBlack)
                       .fillMaxWidth()
                       .padding(10.dp)

               ) {
                   Column(
                       horizontalAlignment = Alignment.End,
                   ) {
                       SongTitleText(text = currentlySelectedSong?.title.toString(), fontSize = 25.sp, color = EerieBlackLight)
                       SongArtistText(text = currentlySelectedSong?.artist.toString(), fontSize = 20.sp, color = EerieBlackLight)
                   }
               }

               Row(
                   verticalAlignment = Alignment.Top,
                   horizontalArrangement = Arrangement.End,
                   modifier = Modifier
                       .background(color = EerieBlack)
                       .fillMaxWidth()
                       .padding(10.dp)

               ) {
                   Column{
                       println("Progress for Song:" )
                       println(state.progress)
                       println(state.progressString)
                       SongSlider(
                           state = state,
                           onSliderPositionChange = {
                               localSliderValue = it
                               //viewModel.onEvent(HomeUiEvent.UpdateProgress(localSliderValue))
                               viewModel.onEvent(SongBarEvent.UpdateProgress(localSliderValue))
                           },
                       )
                   }
               }
               SongControls(context = context)
           }
    }
}

@Composable
fun SongCoverPreview(
    coverUrl: String
) {
    AsyncImage(
        model = coverUrl,
        contentDescription = "image_preview",
        modifier = Modifier
            .fillMaxSize()
    )
}

@Composable
fun SongSlider(
    //state: HomeState,
    state: SongBarState,
    onSliderPositionChange: (Float) -> Unit,
) {
    val totalTime = state.currentlySelectedSongString
    val progressString = state.progressString

    var isChanging by remember { mutableStateOf(false) }
    var localSliderValue by remember { mutableStateOf(0f) }

    Column {
        Slider(
            value = if (isChanging) localSliderValue else state.progress / 100,
            onValueChange = {
                isChanging = true
                localSliderValue = it
                onSliderPositionChange(it)
            },
            onValueChangeFinished = {
                isChanging = false
            }
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = progressString, color = EerieBlackLight)
            Text(text = totalTime, color = EerieBlackLight)
        }
    }
}

@Composable
fun SongControls(
    context: Context,
    viewModel: SongBarViewModel = hiltViewModel()
    //viewModel: HomeViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(color = EerieBlack),
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconButton(onClick = {
            //viewModel.onEvent(HomeUiEvent.SkipToPrevious)
            viewModel.onEvent(SongBarEvent.SkipToPrevious)
        }) {
            Icon(painter = painterResource(id = R.drawable.ic_baseline_skip_previous_24), contentDescription = "skip_prev", tint = EerieBlackLight, modifier = Modifier.size(25.dp))
        }

        IconButton(onClick = {
            //viewModel.onEvent(HomeUiEvent.Backward)
            viewModel.onEvent(SongBarEvent.Backward)
        }) {
            Icon(painter = painterResource(id = R.drawable.ic_baseline_replay_10_24), contentDescription = "skip_prev", tint = EerieBlackLight, modifier = Modifier.size(25.dp))
        }

        IconButton(onClick = {
            //viewModel.onEvent(HomeUiEvent.PlayPause)
            viewModel.onEvent(SongBarEvent.PlayPause)
        }) {
            Icon(painter = painterResource(id = if (state.isPlaying) {R.drawable.ic_baseline_pause_circle_24 } else {R.drawable.ic_baseline_play_arrow_24}
            ), contentDescription = "pause_play", tint = EerieBlackLight, modifier = Modifier.size(45.dp))
        }

        IconButton(onClick = {
            //viewModel.onEvent(HomeUiEvent.Forward)
            viewModel.onEvent(SongBarEvent.Forward)
        }) {
            Icon(painter = painterResource(id = R.drawable.ic_baseline_forward_10_24), contentDescription = "skip_prev", tint = EerieBlackLight, modifier = Modifier.size(25.dp))
        }

        IconButton(onClick = {
            //viewModel.onEvent(HomeUiEvent.SkipToNext)
            viewModel.onEvent(SongBarEvent.SkipToNext)
        }) {
            Icon(painter = painterResource(id = R.drawable.ic_baseline_skip_next_24), contentDescription = "skip_next", tint = EerieBlackLight, modifier = Modifier.size(25.dp))
        }

    }
}