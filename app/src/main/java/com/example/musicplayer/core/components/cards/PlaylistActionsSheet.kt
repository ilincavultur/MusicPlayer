package com.example.musicplayer.core.components.cards

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalDrawer
import androidx.compose.runtime.Composable
import com.example.musicplayer.domain.models.Playlist
import com.example.musicplayer.domain.models.PlaylistWithSongs

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlaylistActionsSheet(
    playlistWithSongs: PlaylistWithSongs,
    onDismissSheet: () -> Unit
) {
//    ModalDrawer(drawerContent =) {
//
//    }
//    ModalBottomSheetLayout(
//        onDismissRequest = {
//            showBottomSheet = false
//        },
//        sheetState = sheetState
//    ) {
//    }
}