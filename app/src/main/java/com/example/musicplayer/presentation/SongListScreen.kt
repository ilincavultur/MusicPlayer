package com.example.musicplayer.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.musicplayer.core.components.cards.SongListCard
import com.example.musicplayer.ui.theme.EerieBlack
import com.example.musicplayer.ui.theme.EerieBlackLight
import com.example.musicplayer.ui.theme.EerieBlackLightTransparent

@Composable
fun SongListScreen(
    navController: NavController
) {

    val test_list = listOf<String>(
        "first",
        "second",
        "third",
        "fourth",
    )

    Surface(
        modifier = Modifier.fillMaxSize().background(color = EerieBlack)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize().background(color = EerieBlack)
        ) {
            items(test_list) { song ->
                SongListCard(song, modifier = Modifier.fillMaxSize())
                Divider(color = EerieBlackLightTransparent)
            }
        }
    }



}