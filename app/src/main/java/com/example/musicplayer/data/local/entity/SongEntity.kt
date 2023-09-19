package com.example.musicplayer.data.local.entity

import android.graphics.BitmapFactory
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.musicplayer.domain.models.Song

@Entity(tableName = "song_entity")
data class SongEntity(
    @PrimaryKey
    val id: Int? = null,
    val title: String? = "",
    val fileName: String? = "",
    val artist: String? = "",
    val coverUrl: String? = "",
    val songUrl: String? = "",
    val duration: Int? = 0
)

fun SongEntity.toSong() : Song {
    return Song(
        mediaId = id.toString(),
        title = title,
        fileName = fileName,
        artist = artist,
        coverUrl = coverUrl,
        songUrl = songUrl,
        duration = duration
    )
}
