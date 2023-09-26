package com.example.musicplayer.domain.usecases

data class PlaylistUsecases(
    val getPlaylists: GetPlaylistsUsecase,
    val getPlaylist: GetPlaylistUsecase,
    val createPlaylist: CreatePlaylistUsecase,
    val addSongsToPlaylist: AddSongsToPlaylistUsecase
)