package com.example.musicplayer.core.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.musicplayer.R
import com.example.musicplayer.domain.models.Playlist
import com.example.musicplayer.domain.models.PlaylistWithSongs
import com.example.musicplayer.ui.theme.EerieBlack
import com.example.musicplayer.ui.theme.EerieBlackLight
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistActionsSheet(
    playlistWithSongs: PlaylistWithSongs,
    onDeleteButtonClick: () -> Unit,
    onChooseCoverPhotoClick: () -> Unit,
    onDismissSheet: () -> Unit,
    //modalSheetState: ModalBottomSheetState,
    coroutineScope: CoroutineScope
) {
    val modalBottomSheetState = rememberSheetState()
    ModalBottomSheet(
        onDismissRequest = onDismissSheet,
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        containerColor = EerieBlack
    ) {
        Surface(
            modifier = Modifier
                .height(100.dp)
                .background(
                    color = EerieBlack
                ),
        ) {
            Column(
                modifier = Modifier.background(
                    color = EerieBlack
                )
            ) {
                Button(
                    onClick = onDeleteButtonClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = EerieBlack,
                        contentColor = Color.White,
                        disabledContainerColor = Color.Transparent,
                        disabledContentColor = Color.White
                    ),
                    modifier = Modifier
                        .align(Alignment.Start)
                        .background(
                            color = EerieBlack
                        )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_delete_outline_24),
                        contentDescription = "delete playlist",
                        tint = EerieBlackLight
                    )
                    Text(
                        text = "Delete Playlist " + playlistWithSongs.playlist.playlistName,
                        style = TextStyle(
                            color = EerieBlackLight,
                            fontSize = 18.sp
                        )
                    )
                }

                Button(
                    onClick = onChooseCoverPhotoClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = EerieBlack,
                        contentColor = Color.White,
                        disabledContainerColor = Color.Transparent,
                        disabledContentColor = Color.White
                    ),
                    modifier = Modifier
                        .align(Alignment.Start)
                        .background(
                            color = EerieBlack
                        )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_add_photo_alternate_24),
                        contentDescription = "choose cover photo",
                        tint = EerieBlackLight
                    )
                    Text(
                        text = "Choose Cover Photo",
                        style = TextStyle(
                            color = EerieBlackLight,
                            fontSize = 18.sp
                        )
                    )
                }
            }
        }
    }
}