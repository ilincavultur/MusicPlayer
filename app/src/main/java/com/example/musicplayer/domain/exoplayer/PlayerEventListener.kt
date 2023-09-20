package com.example.musicplayer.domain.exoplayer

import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class PlayerEventListener @Inject constructor(
    val exoPlayer: ExoPlayer
) : Player.Listener {

    private val _state : MutableStateFlow<PlayerState> = MutableStateFlow(PlayerState.Initial)
    val state: MutableStateFlow<PlayerState> = _state

    private var progressJob: Job? = null

    init {
        exoPlayer.addListener(this)
    }

    fun setMediaItems(mediaItems: List<MediaItem>) {
        exoPlayer.setMediaItems(mediaItems)
        exoPlayer.prepare()
    }

    override fun onPlaybackStateChanged(playbackState: Int) {
        when (playbackState) {
            ExoPlayer.STATE_BUFFERING -> {
                _state.value = PlayerState.Buffering(progress = exoPlayer.currentPosition)
            }
            ExoPlayer.STATE_READY -> {
                _state.value = PlayerState.Ready(duration = exoPlayer.duration)
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onIsPlayingChanged(isPlaying: Boolean) {
        _state.value = PlayerState.Playing(isPlaying = isPlaying)
        _state.value = PlayerState.CurrentlyPlaying(mediaItemIdx = exoPlayer.currentMediaItemIndex)
        if (isPlaying) {
            GlobalScope.launch (Dispatchers.Main) {
                startProgressUpdate()
            }

        } else {
            stopProgressUpdate()
        }
    }

    private suspend fun startProgressUpdate() = progressJob.run {
        while (true) {
            delay(500)
            _state.value = PlayerState.Progress(exoPlayer.currentPosition)
        }
    }

    private fun stopProgressUpdate() {
        progressJob?.cancel()
        _state.value = PlayerState.Playing(isPlaying = false)
    }

    override fun onPlayerError(error: PlaybackException) {
        val cause = error.cause
        System.out.println(cause?.message)
    }

    suspend fun onEvent(event: PlayerEvent) {
        when(event) {
            PlayerEvent.Backward -> {
                exoPlayer.seekBack()
            }
            PlayerEvent.Forward -> {
                exoPlayer.seekForward()
            }
            PlayerEvent.PlayPause -> {
                playPause()
            }
            PlayerEvent.Stop -> {
                exoPlayer.stop()
                _state.value = PlayerState.Playing(isPlaying = false)
                stopProgressUpdate()
            }
            is PlayerEvent.UpdateProgress -> {
                exoPlayer.seekTo((exoPlayer.duration * event.updatedProgress).toLong())
            }
            is PlayerEvent.SeekTo -> {
                exoPlayer.seekTo(event.seekPos)
            }
            is PlayerEvent.SelectAudio -> {
                when(event.selectedMediaIdx) {
                    exoPlayer.currentMediaItemIndex -> {
                        playPause()
                    }
                    else -> {
                        exoPlayer.seekToDefaultPosition(event.selectedMediaIdx)
                        _state.value = PlayerState.Playing(isPlaying = true)
                        exoPlayer.playWhenReady = true
                        startProgressUpdate()
                    }
                }
            }
            PlayerEvent.SkipToPrevious -> {
                val prevIdx = exoPlayer.currentMediaItemIndex - 1
                if (prevIdx >= 0) {
                    exoPlayer.seekToDefaultPosition(prevIdx)
                    _state.value = PlayerState.Playing(isPlaying = true)
                    exoPlayer.playWhenReady = true
                    startProgressUpdate()
                }
            }
            PlayerEvent.SkipToNext -> {
                val nextIdx = exoPlayer.currentMediaItemIndex + 1
                if (nextIdx >= 0 && nextIdx < exoPlayer.mediaItemCount) {
                    exoPlayer.seekToDefaultPosition(nextIdx)
                    _state.value = PlayerState.Playing(isPlaying = true)
                    exoPlayer.playWhenReady = true
                    startProgressUpdate()
                }
            }
        }
    }

    private suspend fun playPause() {
        if (exoPlayer.isPlaying) {
            exoPlayer.pause()
            stopProgressUpdate()
        } else {
            exoPlayer.play()
            _state.value = PlayerState.Playing(isPlaying = true)
            startProgressUpdate()
        }
    }
}