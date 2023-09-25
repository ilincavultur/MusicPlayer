package com.example.musicplayer.data.local.entity

import androidx.room.Entity


@Entity(primaryKeys = ["playlistId", "songId"])
data class PlaylistSongCrossRefEntity(
    val playlistId: Int,
    val songId: Int
)