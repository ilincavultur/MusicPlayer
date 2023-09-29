package com.example.musicplayer.presentation.playlist_detail

import com.example.musicplayer.domain.models.Playlist
import com.example.musicplayer.domain.models.Song
import com.example.musicplayer.presentation.home.HomeUiEvent
import com.example.musicplayer.presentation.playlist.PlaylistScreenEvent

sealed class PlaylistDetailEvent {
    data class ShowSnackbar(val message: String) : PlaylistDetailEvent()

    data class SelectAudio(val selectedMediaIdx : Int  = -1, val mediaId: Int) : PlaylistDetailEvent()
    object ToggleSelectSongsDialog: PlaylistDetailEvent()
    data class CheckSong(val songId: Int, val checked: Boolean) : PlaylistDetailEvent()
    object OnCancelSelectSongsClick: PlaylistDetailEvent()
    object AddToPlaylist: PlaylistDetailEvent()
}