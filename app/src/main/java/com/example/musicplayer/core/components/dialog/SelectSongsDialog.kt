package com.example.musicplayer.core.components.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.musicplayer.core.components.cards.CheckableSongCard
import com.example.musicplayer.domain.models.PlaylistWithSongs
import com.example.musicplayer.domain.models.Song
import com.example.musicplayer.domain.models.toCheckableSong
import com.example.musicplayer.ui.theme.EerieBlack
import com.example.musicplayer.ui.theme.EerieBlackMedium
import com.example.musicplayer.ui.theme.PurpleAccent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectSongsDialog(
    songs: List<Song>,
    isSongChecked: (Int) -> Boolean,
    onDismissRequest: () -> Unit,
    onCancelClick: () -> Unit,
    onSongCardClick: () -> Unit,
    onCheckedChanged: (Int, Boolean) -> Unit,
    onAddToPlaylistClick: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        modifier = Modifier.size(450.dp)
    ) {
        Column(
            modifier = Modifier
                //.fillMaxSize()
                .background(
                    color = EerieBlack
                )
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp, 10.dp, 10.dp, 0.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                LazyColumn {
                    itemsIndexed(songs) { index, song ->
                        CheckableSongCard(
                            song = song.toCheckableSong(),
                            isSongChecked = {
                                   isSongChecked(it)
                            },
                            modifier = Modifier,
                            onSongCardClick = onSongCardClick,
                            onCheckedChanged = { songId, checked ->
                                onCheckedChanged(songId, checked)
                            }
                        )
                    }
                }
            }


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                    horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = onAddToPlaylistClick,
                    colors = ButtonDefaults.buttonColors(
                        contentColor = White,
                        containerColor = PurpleAccent
                    )
                ) {
                    Text("Add To Playlist")
                }
                Spacer(modifier = Modifier.size(10.dp))
                Button(
                    onClick = onCancelClick,
//                        {
//                            viewModel.openDialog.value = false
//                            viewModel.checkableState.clear()
//                            viewModel.checkableState.putAll(getCheckableListStates(viewModel.checkableList))
//                        },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = White,
                        containerColor = EerieBlackMedium
                    )

                ) {
                    Text("Cancel")
                }
            }
        }
    }

}