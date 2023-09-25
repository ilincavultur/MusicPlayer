package com.example.musicplayer.presentation.playlist

import com.example.musicplayer.domain.models.Playlist
import com.example.musicplayer.domain.models.Song

sealed class PlaylistScreenEvent {
    data class ShowSnackbar(val message: String) : PlaylistScreenEvent()
    data class CreatePlaylist(val playlistName: String) : PlaylistScreenEvent()
    data class AddSongsToPlaylist(val playlist: Playlist, val songs: List<Song>) : PlaylistScreenEvent()
}