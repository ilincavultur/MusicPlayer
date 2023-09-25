package com.example.musicplayer.data.local.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.musicplayer.domain.models.PlaylistWithSongs

data class PlaylistWithSongsEntity(
    @Embedded val playlist: PlaylistEntity,
    @Relation(
        parentColumn = "playlistId",
        entityColumn = "songId",
        associateBy = Junction(PlaylistSongCrossRefEntity::class)
    )
    val songs: List<SongEntity>
)

fun PlaylistWithSongsEntity.toPlaylistWithSongs() : PlaylistWithSongs {
    return PlaylistWithSongs(
        playlist = playlist.toPlaylist(),
        songs = songs.map {
            it.toSong()
        }
    )
}

