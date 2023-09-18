package com.example.musicplayer.presentation.home

sealed class HomeUiEvent {
    data class ShowSnackbar(val message: String) : HomeUiEvent()
}
