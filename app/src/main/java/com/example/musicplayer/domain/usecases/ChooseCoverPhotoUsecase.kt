package com.example.musicplayer.domain.usecases

import com.example.musicplayer.domain.models.Playlist
import com.example.musicplayer.domain.models.Song
import com.example.musicplayer.domain.models.toPlaylistEntity
import com.example.musicplayer.domain.models.toSongEntity
import com.example.musicplayer.domain.repository.PlaylistRepository
import javax.inject.Inject

class ChooseCoverPhotoUsecase @Inject constructor(
    private val repository: PlaylistRepository
) {
    suspend operator fun invoke(playlist: Playlist) {
        return repository.updatePlaylist(playlist.toPlaylistEntity())
    }
}