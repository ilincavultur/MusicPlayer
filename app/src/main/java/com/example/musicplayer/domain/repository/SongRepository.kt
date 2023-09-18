package com.example.musicplayer.domain.repository

import com.example.musicplayer.core.util.Resource
import com.example.musicplayer.domain.models.Song
import kotlinx.coroutines.flow.Flow

interface SongRepository {
    suspend fun insertSong(song: Song)

    suspend fun deleteSong(song: Song)

    suspend fun getSongs() : Flow<Resource<List<Song>>>

    suspend fun exists(id: Int) : Boolean
}