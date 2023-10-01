package com.example.musicplayer.presentation.playlist

import com.example.musicplayer.domain.models.PlaylistWithSongs

data class PlaylistScreenState(
    var isLoading: Boolean = false,
    val playlists: List<PlaylistWithSongs> = emptyList(),
    val isCreateDialogOpen: Boolean = false,
    val dialogText: String = "",
    val isError: Boolean = false,
    val contextMenuPlaylistId: Int? = null,
)