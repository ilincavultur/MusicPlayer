package com.example.musicplayer.domain.models

data class CheckableSong (
    val id: Int,
    val title: String,
    val artist: String,
    val coverUrl: String,
    var checked: Boolean,
)

fun Song.toCheckableSong() : CheckableSong {
    return CheckableSong(
        id = mediaId?.toInt() ?: -1,
        title = title.toString(),
        artist = artist.toString(),
        coverUrl = coverUrl.toString(),
        checked = false
    )
}
