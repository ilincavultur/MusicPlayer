package com.example.musicplayer.presentation.playlist

import android.content.Context
import com.example.musicplayer.domain.models.Playlist
import com.example.musicplayer.domain.models.Song

sealed class PlaylistScreenEvent {
    data class ShowSnackbar(val message: String) : PlaylistScreenEvent()
    object CreatePlaylist : PlaylistScreenEvent()
    object ToggleCreateDialog: PlaylistScreenEvent()
    data class UpdateDialogText(val playlistName: String) : PlaylistScreenEvent()
    data class OnCardLongClick(val playlistId: Int) : PlaylistScreenEvent()
    object DeletePlaylist : PlaylistScreenEvent()
    object OnDismissSheet : PlaylistScreenEvent()
    data class SetCoverPhoto(val context: Context, val playlistCoverPhoto: String) : PlaylistScreenEvent()
}