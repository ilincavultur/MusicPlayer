package com.example.musicplayer.presentation.song_bar

sealed class SongBarEvent {
    data class ShowSnackbar(val message: String) : SongBarEvent()

    object PlayPause : SongBarEvent()
    object SkipToNext : SongBarEvent()
    object SkipToPrevious : SongBarEvent()
    object Backward : SongBarEvent()
    object Forward : SongBarEvent()
    data class SeekTo(val seekPos: Long = 0) : SongBarEvent()
    object Stop : SongBarEvent()
    data class UpdateProgress(val updatedProgress: Float) : SongBarEvent()
    object ToggleFullScreenMode : SongBarEvent()
}