package com.example.musicplayer.di

import android.app.PendingIntent
import android.content.Context
import androidx.media3.common.AudioAttributes

import androidx.media3.common.C
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import androidx.media3.session.MediaSession
import androidx.room.Room
import com.example.musicplayer.data.local.SongDao
import com.example.musicplayer.data.local.db.RoomSongsDb
import com.example.musicplayer.data.remote.db.FirestoreSongsDb
import com.example.musicplayer.data.repository.SongRepositoryImpl
import com.example.musicplayer.domain.exoplayer.PlayerEventListener
import com.example.musicplayer.domain.notification.MusicNotificationAdapter
import com.example.musicplayer.domain.notification.MusicNotificationManager
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

    @Provides
    @Singleton
    fun provideAudioAttributes(): AudioAttributes = AudioAttributes.Builder()
        .setContentType(C.AUDIO_CONTENT_TYPE_MOVIE)
        .setUsage(C.USAGE_MEDIA)
        .build()

    @ServiceScoped
    @Provides
    @UnstableApi
    fun provideExoPlayer(
        @ApplicationContext context: Context,
        audioAttributes: AudioAttributes
    ) = ExoPlayer.Builder(context)
        .setAudioAttributes(audioAttributes, true)
        .setHandleAudioBecomingNoisy(true)
        .setTrackSelector(DefaultTrackSelector(context))
        .build()

    @Provides
    @Singleton
    fun provideMediaSession(
        @ApplicationContext context: Context,
        player: ExoPlayer,
    ): MediaSession = MediaSession.Builder(context, player).build()

    @Provides
    @Singleton
    fun provideNotificationManager(
        @ApplicationContext context: Context,
        player: ExoPlayer,
    ): MusicNotificationManager = MusicNotificationManager(
        context = context,
        exoPlayer = player
    )

    @Provides
    @Singleton
    fun providePlayerListener(exoPlayer: ExoPlayer): PlayerEventListener =
        PlayerEventListener(exoPlayer)

}