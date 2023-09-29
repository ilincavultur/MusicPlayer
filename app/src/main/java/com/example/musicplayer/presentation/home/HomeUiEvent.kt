package com.example.musicplayer.presentation.home

import com.example.musicplayer.domain.exoplayer.PlayerEvent

sealed class HomeUiEvent {
    data class ShowSnackbar(val message: String) : HomeUiEvent()

    data class SelectAudio(val selectedMediaIdx : Int  = -1, val mediaId: Int) : HomeUiEvent()
    object PlayPause : HomeUiEvent()
    object SkipToNext : HomeUiEvent()
    object SkipToPrevious : HomeUiEvent()
    object Backward : HomeUiEvent()
    object Forward : HomeUiEvent()
    data class SeekTo(val seekPos: Long = 0) : HomeUiEvent()
    object Stop : HomeUiEvent()
    data class UpdateProgress(val updatedProgress: Float) : HomeUiEvent()
    object ToggleFullScreenMode : HomeUiEvent()
}
