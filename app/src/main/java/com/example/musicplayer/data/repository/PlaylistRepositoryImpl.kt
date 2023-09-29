package com.example.musicplayer.data.repository

import com.example.musicplayer.core.util.Resource
import com.example.musicplayer.data.local.PlaylistDao
import com.example.musicplayer.data.local.entity.*
import com.example.musicplayer.domain.models.Playlist
import com.example.musicplayer.domain.models.PlaylistWithSongs
import com.example.musicplayer.domain.models.toPlaylistEntity
import com.example.musicplayer.domain.repository.PlaylistRepository
import kotlinx.coroutines.flow.*
import java.lang.Thread.State
import javax.inject.Inject

class PlaylistRepositoryImpl @Inject constructor(
    private val dao: PlaylistDao,
) : PlaylistRepository {
    override fun getPlaylistsWithSongs(): Flow<Resource<List<PlaylistWithSongs>>> = flow {
        emit(Resource.Loading())

        val playlists = dao.getPlaylistsWithSongs().map {
            it.toPlaylistWithSongs()
        }

        emit(Resource.Loading(data = playlists))

        emit(Resource.Success(playlists))

    }

    override fun getPlaylistWithSongs(id: Int): Flow<Resource<PlaylistWithSongs>> = flow {
        emit(Resource.Loading())

        val playlist = dao.getPlaylistsWithSongs().map {
            it.toPlaylistWithSongs()
        }.first {
            it.playlist.playlistId == id
        }

        emit(Resource.Loading(data = playlist))

        emit(Resource.Success(playlist))
    }

    override suspend fun getPlaylistWithSongsStateFlow(id: Int): PlaylistWithSongs? {
        val playlist = dao.getPlaylistsWithSongs().map {
            it.toPlaylistWithSongs()
        }.first {
            it.playlist.playlistId == id
        }

        return playlist ?: null
    }

    // Create with or without songs
    override suspend fun createPlaylist(playlist: PlaylistEntity, songs: List<SongEntity>) {
        val playlistId = dao.insertPlaylist(playlist)
        println("new playlistId: " + playlistId)
        songs.forEach {
            val songId = dao.insertSong(it)
            dao.insertPlaylistSongCrossRef(
                PlaylistSongCrossRefEntity(
                    playlistId = playlistId.toInt(),
                    songId = songId.toInt()
                )
            )
        }
    }

    // Add songs
    override suspend fun addSongsToPlaylist(playlist: PlaylistEntity, songs: List<SongEntity>) {
        playlist.playlistId?.let { id ->
            songs.forEach {
                val songId = dao.insertSong(it)
                dao.insertPlaylistSongCrossRef(
                    PlaylistSongCrossRefEntity(
                        playlistId = id,
                        songId = songId.toInt()
                    )
                )
            }
        }

    }

    override suspend fun deletePlaylist(id: Int) {
        dao.deletePlaylist(id)
    }

    override suspend fun updatePlaylist(playlist: PlaylistEntity) {
        dao.updatePlaylist(playlist)
    }

}
