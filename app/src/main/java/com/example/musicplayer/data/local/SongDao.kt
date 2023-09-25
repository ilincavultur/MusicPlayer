package com.example.musicplayer.data.local

import androidx.room.*
import com.example.musicplayer.data.local.entity.SongEntity

@Dao
interface SongDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSongs(song: List<SongEntity>)

    @Delete
    suspend fun deleteSong(song: SongEntity)

    @Query("DELETE FROM song_entity WHERE songId IN(:songIds)")
    suspend fun deleteSongs(songIds: List<Int>)

    @Query("SELECT * FROM song_entity")
    suspend fun getSongs(): List<SongEntity>

    @Query("SELECT EXISTS (SELECT 1 FROM song_entity WHERE songId = :id)")
    suspend fun exists(id: Int): Boolean

    @Query("SELECT (SELECT COUNT(*) FROM song_entity) == 0")
    fun isEmpty(): Boolean
}