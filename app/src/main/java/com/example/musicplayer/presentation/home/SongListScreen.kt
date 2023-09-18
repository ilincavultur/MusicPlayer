package com.example.musicplayer.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.musicplayer.core.components.cards.SongListCard
import com.example.musicplayer.presentation.home.HomeViewModel
import com.example.musicplayer.ui.theme.EerieBlack
import com.example.musicplayer.ui.theme.EerieBlackLight
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
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = EerieBlack)
                ) {
                    items(state.songs) { song ->
                        SongListCard(song.title.toString(), modifier = Modifier.fillMaxSize())
                        Divider(color = EerieBlackLightTransparent)
                    }
                }
            }
        }
    }
}