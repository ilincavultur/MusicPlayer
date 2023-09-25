package com.example.musicplayer.domain.usecases

import com.example.musicplayer.core.util.Resource
import com.example.musicplayer.data.local.entity.PlaylistEntity
import com.example.musicplayer.data.local.entity.SongEntity
import com.example.musicplayer.domain.models.Playlist
import com.example.musicplayer.domain.models.Song
import com.example.musicplayer.domain.models.toPlaylistEntity
import com.example.musicplayer.domain.models.toSongEntity
import com.example.musicplayer.domain.repository.PlaylistRepository
import com.example.musicplayer.domain.repository.SongRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreatePlaylistUsecase @Inject constructor(
    private val repository: PlaylistRepository
) {
    suspend operator fun invoke(playlist: Playlist, songs: List<Song>) {
        return repository.createPlaylist(playlist.toPlaylistEntity(), songs.map {
            it.toSongEntity()
        })
    }
}