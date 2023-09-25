package com.example.musicplayer.domain.usecases

import com.example.musicplayer.core.util.Resource
import com.example.musicplayer.domain.models.Playlist
import com.example.musicplayer.domain.models.PlaylistWithSongs
import com.example.musicplayer.domain.models.Song
import com.example.musicplayer.domain.repository.PlaylistRepository
import com.example.musicplayer.domain.repository.SongRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPlaylistsUsecase @Inject constructor(
    private val repository: PlaylistRepository
) {
    operator fun invoke() : Flow<Resource<List<PlaylistWithSongs>>> {
        return repository.getPlaylistsWithSongs()
    }
}