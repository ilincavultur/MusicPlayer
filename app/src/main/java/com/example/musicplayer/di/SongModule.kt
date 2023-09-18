package com.example.musicplayer.di

import android.content.Context
import androidx.room.Room
import com.example.musicplayer.data.local.SongDao
import com.example.musicplayer.data.local.db.RoomSongsDb
import com.example.musicplayer.data.remote.db.FirestoreSongsDb
import com.example.musicplayer.data.repository.SongRepositoryImpl
import com.example.musicplayer.domain.repository.SongRepository
import com.example.musicplayer.domain.usecases.GetSongsUsecase
import com.example.musicplayer.domain.usecases.SongUsecases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SongModule {
    @Singleton
    @Provides
    fun provideRoomSongDatabase(
        @ApplicationContext appContext: Context
    ): RoomSongsDb {
        return Room.databaseBuilder(
            appContext,
            RoomSongsDb::class.java,
            "song_db.db"
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideFirestoreSongDatabase() = FirestoreSongsDb()

    @Singleton
    @Provides
    fun provideSongDao(
        database: RoomSongsDb
    ) = database.dao

    @Singleton
    @Provides
    fun provideSongRepository(
        dao: SongDao,
        firestoreSongsDb: FirestoreSongsDb
    ): SongRepository {
        return SongRepositoryImpl(dao, firestoreSongsDb)
    }

    @Provides
    @Singleton
    fun provideSongUsecases(repository: SongRepository): SongUsecases {
        return SongUsecases(
            getSongs = GetSongsUsecase(repository)
        )
    }
}