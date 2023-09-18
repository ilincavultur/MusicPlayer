package com.example.musicplayer.presentation.home

import com.example.musicplayer.domain.models.Song

data class HomeState(
    var isLoading: Boolean = false,
    val songs: List<Song> = emptyList()
)
