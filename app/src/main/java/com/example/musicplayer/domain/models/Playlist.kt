package com.example.musicplayer.domain.models

import androidx.room.PrimaryKey
import com.example.musicplayer.data.local.entity.PlaylistEntity

data class Playlist(
    val playlistId: Int? = null,
    val playlistName: String = "",
    val playlistCoverPhoto: String = "",
)

fun Playlist.toPlaylistEntity() : PlaylistEntity {
    return PlaylistEntity(
        playlistId = playlistId,
        playlistName = playlistName,
        playlistCoverPhoto = playlistCoverPhoto
    )
}