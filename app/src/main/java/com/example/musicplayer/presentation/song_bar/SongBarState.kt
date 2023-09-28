package com.example.musicplayer.presentation.song_bar

import com.example.musicplayer.domain.models.Song

data class SongBarState(
    val songs: List<Song> = emptyList(),
    var currentlySelectedSong: Song? = null,
    var currentlySelectedSongString: String = "00:00",
    var isInFullScreenMode: Boolean = false,
    var duration: Long = 0,
    var progress: Float = 0f,
    var progressString: String = "00:00",
    var isPlaying: Boolean = false,

)