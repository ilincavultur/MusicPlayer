package com.example.musicplayer.domain.exoplayer

import android.content.ComponentName
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaController
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.MoreExecutors
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

// Source: https://github.com/Hoodlab/Jet_Audio/tree/final
@AndroidEntryPoint
class MusicService : MediaSessionService() {

    @Inject
    lateinit var mediaSession: MediaSession

    @Inject
    lateinit var notificationManager: com.example.musicplayer.domain.notification.MusicNotificationManager

//    override fun onCreate() {
//        super.onCreate()
//        val sessionToken = SessionToken(this, ComponentName(this, MusicService::class.java))
//        val controllerFuture = MediaController.Builder(this, sessionToken).buildAsync()
//        controllerFuture.addListener(
//            {
//
//                // Call controllerFuture.get() to retrieve the MediaController.
//                // MediaController implements the Player interface, so it can be
//                // attached to the PlayerView UI component.
//                //playerView.setPlayer(controllerFuture.get())
//            },
//            MoreExecutors.directExecutor()
//        )
//    }

    @RequiresApi(Build.VERSION_CODES.O)
    @UnstableApi
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> start()
            ACTION_STOP -> stop()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @UnstableApi
    private fun start() {
        notificationManager.startNotificationService(
            mediaSessionService = this,
            mediaSession =  mediaSession
        )
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun stop() {
        notificationManager.stopNotificationService(
            mediaSessionService = this
        )
    }

    override fun onDestroy() {
        mediaSession.run {
            player.release()
            release()
            if (player.playbackState != Player.STATE_IDLE) {
                player.seekTo(0)
                player.playWhenReady = false
                player.stop()
            }
        }
        super.onDestroy()
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? = mediaSession

    companion object {
        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"
    }
}
// Source: https://github.com/Hoodlab/Jet_Audio/tree/final