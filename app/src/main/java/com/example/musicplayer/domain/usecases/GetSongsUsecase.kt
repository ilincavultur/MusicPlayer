package com.example.musicplayer.domain.usecases

import com.example.musicplayer.core.util.Resource
import com.example.musicplayer.domain.models.Song
import com.example.musicplayer.domain.repository.SongRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetSongsUsecase @Inject constructor(
    private val repository: SongRepository
) {
    suspend operator fun invoke() : Flow<Resource<List<Song>>> {
        return repository.getSongs()
    }
}