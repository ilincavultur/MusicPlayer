package com.example.musicplayer.domain.models

import com.example.musicplayer.data.local.entity.PlaylistWithSongsEntity

data class PlaylistWithSongs(
    val playlist: Playlist,
    val songs: List<Song> = emptyList()
)

fun PlaylistWithSongs.toPlaylistWithSongsEntity() : PlaylistWithSongsEntity {
    return PlaylistWithSongsEntity(
        playlist = playlist.toPlaylistEntity(),
        songs = songs.map {
            it.toSongEntity()
        }
    )
}