package com.example.musicplayer.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.musicplayer.data.local.SongDao
import com.example.musicplayer.data.local.entity.SongEntity

@Database(
    entities = [SongEntity::class],
    version = 1
)
abstract class RoomSongsDb: RoomDatabase() {
    abstract val dao: SongDao
}