package com.example.musicplayer.domain.usecases

import com.example.musicplayer.core.util.Resource
import com.example.musicplayer.domain.models.PlaylistWithSongs
import com.example.musicplayer.domain.repository.PlaylistRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPlaylistUsecase @Inject constructor(
    private val repository: PlaylistRepository
) {
    operator fun invoke(id: Int) : Flow<Resource<PlaylistWithSongs>> {
        return repository.getPlaylistWithSongs(id)
    }
}