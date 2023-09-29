package com.example.musicplayer.domain.exoplayer

sealed class PlayerEvent {
    data class SelectAudio(val selectedMediaIdx : Int  = -1, val mediaId: Int) : PlayerEvent()
    object PlayPause : PlayerEvent()
    object SkipToNext : PlayerEvent()
    object SkipToPrevious : PlayerEvent()
    object Backward : PlayerEvent()
    object Forward : PlayerEvent()
    data class SeekTo(val seekPos: Long = 0) : PlayerEvent()
    object Stop : PlayerEvent()
    data class UpdateProgress(val updatedProgress: Float) : PlayerEvent()
}
