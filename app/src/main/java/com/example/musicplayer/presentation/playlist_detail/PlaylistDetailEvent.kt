package com.example.musicplayer.presentation.playlist_detail

import com.example.musicplayer.domain.models.Playlist
import com.example.musicplayer.domain.models.Song
import com.example.musicplayer.presentation.home.HomeUiEvent
import com.example.musicplayer.presentation.playlist.PlaylistScreenEvent

sealed class PlaylistDetailEvent {
    data class ShowSnackbar(val message: String) : PlaylistDetailEvent()

    data class SelectAudio(val selectedMediaIdx : Int  = -1, val mediaId: Int) : PlaylistDetailEvent()
    object PlayPause : PlaylistDetailEvent()
    object SkipToNext : PlaylistDetailEvent()
    object SkipToPrevious : PlaylistDetailEvent()
    object Backward : PlaylistDetailEvent()
    object Forward : PlaylistDetailEvent()
    data class SeekTo(val seekPos: Long = 0) : PlaylistDetailEvent()
    object Stop : PlaylistDetailEvent()
    data class UpdateProgress(val updatedProgress: Float) : PlaylistDetailEvent()
    object ToggleFullScreenMode : PlaylistDetailEvent()
    object ToggleSelectSongsDialog: PlaylistDetailEvent()
    data class CheckSong(val songId: Int, val checked: Boolean) : PlaylistDetailEvent()
    object OnCancelSelectSongsClick: PlaylistDetailEvent()
    object AddToPlaylist: PlaylistDetailEvent()
}