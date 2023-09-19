package com.example.musicplayer.domain.exoplayer

import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.datasource.HttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import javax.inject.Inject

class PlayerEventListener @Inject constructor(
    val exoPlayer: ExoPlayer
) : Player.Listener {
    override fun onIsPlayingChanged(isPlaying: Boolean) {
        if (isPlaying) {
            // Active playback.
        } else {
            // Not playing because playback is paused, ended, suppressed, or the player
            // is buffering, stopped or failed. Check player.playWhenReady,
            // player.playbackState, player.playbackSuppressionReason and
            // player.playerError for details.
        }
    }

    override fun onPlayerError(error: PlaybackException) {
        val cause = error.cause
        if (cause is HttpDataSource.HttpDataSourceException) {
            // An HTTP error occurred.
            val httpError = cause
            // It's possible to find out more about the error both by casting and by querying
            // the cause.
            if (httpError is HttpDataSource.InvalidResponseCodeException) {
                // Cast to InvalidResponseCodeException and retrieve the response code, message
                // and headers.
            } else {
                // Try calling httpError.getCause() to retrieve the underlying cause, although
                // note that it may be null.
            }
        }
    }
}