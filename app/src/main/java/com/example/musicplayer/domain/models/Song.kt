package com.example.musicplayer.domain.models

import com.example.musicplayer.data.local.entity.SongEntity

data class Song(
    val mediaId: String? = null,
    val title: String? = "",
    val fileName: String? = "",
    val artist: String? = "",
    val coverUrl: String? = "",
    val songCover: String? = "",
    val songUrl: String? = "",
    val duration: Int? = 0
)

fun Song.toSongEntity() : SongEntity {
    return SongEntity(
        songId = mediaId?.toInt(),
        title = title,
        fileName = fileName,
        artist = artist,
        coverUrl = coverUrl,
        songUrl = songUrl,
        duration = duration
    )
}
