package com.example.musicplayer.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.musicplayer.domain.models.Playlist

@Entity(tableName = "playlist_entity")
data class PlaylistEntity(
    @PrimaryKey
    val playlistId: Int? = null,
    val playlistName: String = "",
    val playlistCoverPhoto: String = "",
)

fun PlaylistEntity.toPlaylist() : Playlist {
    return Playlist(
        playlistId = playlistId,
        playlistName = playlistName,
        playlistCoverPhoto = playlistCoverPhoto
    )
}