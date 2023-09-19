package com.example.musicplayer.domain.exoplayer

sealed class PlayerState {
    object Initial : PlayerState()
    data class CurrentlyPlaying(val mediaItemIdx: Int) : PlayerState()
    data class Playing(val isPlaying: Boolean) : PlayerState()
    data class Progress(val progress: Long) : PlayerState()
    data class Buffering(val progress: Long) : PlayerState()
    data class Idle(val progress: Long) : PlayerState()
    data class Ended(val progress: Long) : PlayerState()
    data class Ready(val duration: Long) : PlayerState()
}