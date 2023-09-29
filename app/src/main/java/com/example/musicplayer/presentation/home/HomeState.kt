package com.example.musicplayer.presentation.home

import com.example.musicplayer.domain.models.Song

val songTest = Song(
    mediaId = null,
    title = "Song Title",
    fileName = "File Name",
    artist = "Artist",
    coverUrl = "url",
    songCover = "url",
    songUrl = "url",
    duration = 120,
)

data class HomeState(
    var isLoading: Boolean = false,
    val songs: List<Song> = emptyList(),
    var duration: Long = 0,
    var progress: Float = 0f,
    var progressString: String = "00:00",
    var isPlaying: Boolean = false,
    var currentlySelectedSong: Song? = null,
    var currentlySelectedSongString: String = "00:00",
    //var isInFullScreenMode: Boolean = false,
)

sealed class HomeUiState {
    object Initial : HomeUiState()
    object Ready : HomeUiState()
}
