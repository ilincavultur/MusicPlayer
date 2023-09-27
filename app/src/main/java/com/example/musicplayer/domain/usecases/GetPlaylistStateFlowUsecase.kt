package com.example.musicplayer.domain.usecases

import com.example.musicplayer.core.util.Resource
import com.example.musicplayer.domain.models.PlaylistWithSongs
import com.example.musicplayer.domain.repository.PlaylistRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetPlaylistStateFlowUsecase @Inject constructor(
    private val repository: PlaylistRepository
) {
    suspend operator fun invoke(id: Int) : PlaylistWithSongs? {
        return repository.getPlaylistWithSongsStateFlow(id)
    }
}