package com.example.musicplayer.domain.usecases

import com.example.musicplayer.domain.models.Playlist
import com.example.musicplayer.domain.models.Song
import com.example.musicplayer.domain.models.toPlaylistEntity
import com.example.musicplayer.domain.models.toSongEntity
import com.example.musicplayer.domain.repository.PlaylistRepository
import javax.inject.Inject

class AddSongsToPlaylistUsecase @Inject constructor(
    private val repository: PlaylistRepository
) {
    suspend operator fun invoke(playlist: Playlist, songs: List<Song>) {
        return repository.addSongsToPlaylist(playlist.toPlaylistEntity(), songs.map {
            it.toSongEntity()
        })
    }
}
