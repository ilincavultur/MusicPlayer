package com.example.musicplayer.data.local

import androidx.room.*
import com.example.musicplayer.data.local.entity.PlaylistEntity
import com.example.musicplayer.data.local.entity.PlaylistSongCrossRefEntity
import com.example.musicplayer.data.local.entity.PlaylistWithSongsEntity
import com.example.musicplayer.data.local.entity.SongEntity

@Dao
interface PlaylistDao {
    @Transaction
    @Query("SELECT * FROM playlist_entity")
    suspend fun getPlaylistsWithSongs(): List<PlaylistWithSongsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSong(song: SongEntity) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylist(playlist: PlaylistEntity) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylistSongCrossRef(PlaylistSongCrossRef: PlaylistSongCrossRefEntity) : Long

    @Query("DELETE FROM playlist_entity WHERE playlistId=:id")
    suspend fun deletePlaylist(id: Int)

    @Update
    suspend fun updatePlaylist(playlist: PlaylistEntity)
}
