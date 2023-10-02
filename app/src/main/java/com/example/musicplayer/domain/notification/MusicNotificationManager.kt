package com.example.musicplayer.domain.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.SessionToken
import androidx.media3.session.MediaSessionService
import androidx.media3.ui.PlayerNotificationManager
import com.example.musicplayer.R
import com.example.musicplayer.domain.exoplayer.MusicService
import javax.inject.Inject

// Source: https://github.com/Hoodlab/Jet_Audio/tree/final
class MusicNotificationManager @Inject constructor(
    val context: Context,
    val exoPlayer: ExoPlayer
) {
    private val notificationManager: NotificationManagerCompat =
        NotificationManagerCompat.from(context)

    init {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "music",
                "Music",
                NotificationManager.IMPORTANCE_LOW
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @UnstableApi
    fun startNotificationService(
        mediaSessionService: MediaSessionService,
        mediaSession: MediaSession
    ) {

        // Build Notification
        PlayerNotificationManager.Builder(
            context,
            1,
            "Music"
        ).setMediaDescriptionAdapter(
            MusicNotificationAdapter(
                context,
                mediaSession.sessionActivity
            )
        )
            .setSmallIconResourceId(R.drawable.ic_launcher_background)
            .build()
            .also {
                //it.setMediaSessionToken(mediaSession.sessionCompatToken)
                it.setUseFastForwardActionInCompactView(true)
                it.setUseRewindActionInCompactView(true)
                it.setUseNextActionInCompactView(true)
                it.setPriority(NotificationCompat.PRIORITY_LOW)
                it.setPlayer(exoPlayer)
            }

        // Start Foreground Service
        val notification = Notification.Builder(context, "Music")
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()
        mediaSessionService.startForeground(1, notification)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun stopNotificationService(
        mediaSessionService: MediaSessionService,
    ) {
        // Stop Foreground Service
        mediaSessionService.stopForeground(Service.STOP_FOREGROUND_REMOVE)
        mediaSessionService.stopSelf()

    }

}
// Source: https://github.com/Hoodlab/Jet_Audio/tree/final