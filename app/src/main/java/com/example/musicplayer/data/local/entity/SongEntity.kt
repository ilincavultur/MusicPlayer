package com.example.musicplayer.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "song_entity")
data class SongEntity(
    @PrimaryKey
    val id: Int? = null,
    val title: String? = "",
    val fileName: String? = "",
    val artist: String? = "",
    val coverUrl: String? = "",
    val songUrl: String? = ""
)
