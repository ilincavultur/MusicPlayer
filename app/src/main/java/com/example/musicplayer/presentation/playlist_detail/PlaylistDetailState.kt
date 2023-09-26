package com.example.musicplayer.presentation.playlist_detail

import com.example.musicplayer.domain.models.PlaylistWithSongs
import com.example.musicplayer.domain.models.Song

data class PlaylistDetailState(
    var isLoading: Boolean = false,
    val playlistWithSongs: PlaylistWithSongs? = null,
    val songs: List<Song> = emptyList(),
    var duration: Long = 0,
    var progress: Float = 0f,
    var progressString: String = "00:00",
    var isPlaying: Boolean = false,
    var currentlySelectedSong: Song? = null,
    var currentlySelectedSongString: String = "00:00",
    var isInFullScreenMode: Boolean = false,
)