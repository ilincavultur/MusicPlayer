package com.example.musicplayer.presentation.home

import com.example.musicplayer.domain.exoplayer.PlayerEvent

sealed class HomeUiEvent {
    data class ShowSnackbar(val message: String) : HomeUiEvent()

    data class SelectAudio(val selectedMediaIdx : Int  = -1, val mediaId: Int) : HomeUiEvent()
}
