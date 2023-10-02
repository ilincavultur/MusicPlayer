package com.example.musicplayer.domain.notification

import android.app.PendingIntent
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.PlayerNotificationManager

// Source: https://github.com/Hoodlab/Jet_Audio/tree/final
class MusicNotificationAdapter(
    val context: Context,
    val pendingIntent: PendingIntent?
) : PlayerNotificationManager.MediaDescriptionAdapter {
    @UnstableApi
    override fun getCurrentContentTitle(player: Player): CharSequence {
        return player.mediaMetadata.displayTitle ?: "Unknown"
    }

    @UnstableApi
    override fun createCurrentContentIntent(player: Player): PendingIntent? = pendingIntent

    @UnstableApi
    override fun getCurrentContentText(player: Player): CharSequence? {
        return player.mediaMetadata.artist ?: "Unknown"
    }

    @UnstableApi
    override fun getCurrentLargeIcon(
        player: Player,
        callback: PlayerNotificationManager.BitmapCallback
    ): Bitmap? {
        return null
    }
}
// Source: https://github.com/Hoodlab/Jet_Audio/tree/final