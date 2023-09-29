package com.example.musicplayer.domain.repository

import com.example.musicplayer.core.util.Resource
import com.example.musicplayer.data.local.entity.PlaylistEntity
import com.example.musicplayer.data.local.entity.PlaylistSongCrossRefEntity
import com.example.musicplayer.data.local.entity.PlaylistWithSongsEntity
import com.example.musicplayer.data.local.entity.SongEntity
import com.example.musicplayer.domain.models.Playlist
import com.example.musicplayer.domain.models.PlaylistWithSongs
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface PlaylistRepository {
    fun getPlaylistsWithSongs() : Flow<Resource<List<PlaylistWithSongs>>>

    fun getPlaylistWithSongs(id: Int) : Flow<Resource<PlaylistWithSongs>>

    suspend fun getPlaylistWithSongsStateFlow(id: Int) : PlaylistWithSongs?

    suspend fun createPlaylist(playlist: PlaylistEntity, songs: List<SongEntity>)

    suspend fun addSongsToPlaylist(playlist: PlaylistEntity, songs: List<SongEntity>)

    suspend fun deletePlaylist(id: Int)

    suspend fun updatePlaylist(playlist: PlaylistEntity)
}