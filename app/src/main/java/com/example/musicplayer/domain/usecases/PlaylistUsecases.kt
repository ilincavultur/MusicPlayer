package com.example.musicplayer.domain.usecases

data class PlaylistUsecases(
    val getPlaylists: GetPlaylistsUsecase,
    val getPlaylist: GetPlaylistUsecase,
    val getPlaylistFlowUsecase: GetPlaylistStateFlowUsecase,
    val createPlaylist: CreatePlaylistUsecase,
    val chooseCoverPhoto: ChooseCoverPhotoUsecase,
    val addSongsToPlaylist: AddSongsToPlaylistUsecase,
    val deletePlaylist: DeletePlaylistUsecase
)