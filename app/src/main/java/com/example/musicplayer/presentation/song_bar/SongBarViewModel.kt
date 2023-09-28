package com.example.musicplayer.presentation.song_bar

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.exoplayer.ExoPlayer
import com.example.musicplayer.core.util.Resource
import com.example.musicplayer.domain.exoplayer.PlayerEvent
import com.example.musicplayer.domain.exoplayer.PlayerEventListener
import com.example.musicplayer.domain.exoplayer.PlayerState
import com.example.musicplayer.domain.usecases.GetSongsUsecase
import com.example.musicplayer.presentation.home.HomeState
import com.example.musicplayer.presentation.home.HomeUiEvent
import com.example.musicplayer.presentation.home.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SongBarViewModel @Inject constructor(
    private val exoPlayer: ExoPlayer,
    private val playerEventListener: PlayerEventListener,
    private val songsUsecase: GetSongsUsecase,
) : ViewModel() {
    private val _state = mutableStateOf(SongBarState())
    val state: State<SongBarState> = _state

    //val song = exoPlayer.currentMediaItemIndex

    private var searchJob: Job? = null

    init {
        loadSongs()
    }

    private fun loadSongs() {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500L)
            songsUsecase.invoke()
                .onEach { result ->
                    when (result) {
                        is Resource.Error -> {
                            _state.value = state.value.copy(
                                songs = result.data ?: emptyList(),
                                //isLoading = false
                            )
                        }
                        is Resource.Loading -> {
                            _state.value = state.value.copy(
                                songs = result.data ?: emptyList(),
                                //isLoading = true
                            )
                        }
                        is Resource.Success -> {
                            _state.value = state.value.copy(
                                songs = result.data ?: emptyList(),
                                //isLoading = false
                            )
                            //setMediaItems()
                        }
                    }
                }.launchIn(this)
        }
    }

    init {
        viewModelScope.launch {
            playerEventListener.state.collectLatest { playerState ->
                when (playerState) {
                    is PlayerState.Buffering -> {
                        //viewModelScope.launch {
                        //val newProgress = calculateProgressValue(playerState.progress)
                        val newProgress = playerState.progress
                        _state.value = state.value.copy(
                            progress = newProgress.toFloat(),
                            //progressString = formatDuration(newProgress.toLong())
                            progressString = formatDurationFromMili(newProgress.toLong())
                        )
                        //}
                    }
                    is PlayerState.CurrentlyPlaying -> {
                        println("event listener song bar  " + playerState.mediaItemIdx)
                        _state.value = state.value.copy(
                            currentlySelectedSong = state.value.songs[playerState.mediaItemIdx],
                            //currentlySelectedSongString = formatDuration(state.value.songs[playerState.mediaItemIdx].duration?.toLong() ?: 0)
                            currentlySelectedSongString = formatDurationFromMili(state.value.duration)
                        )
                    }
                    is PlayerState.Ended -> TODO()
                    is PlayerState.Idle -> TODO()
                    PlayerState.Initial -> {
                        //_uiState.value = HomeUiState.Initial
                    }
                    is PlayerState.Playing -> {
                        println("playin song bar  ")
                        _state.value = state.value.copy(
                            isPlaying = playerState.isPlaying
                        )
                    }
                    is PlayerState.Progress -> {
                        //viewModelScope.launch {
                        val newProgress = calculateProgressValue(playerState.progress)
                        val newProgressString = formatDuration(state.value.progress.toLong())
                        withContext(Dispatchers.Main) {
                            _state.value = state.value.copy(
                                progress = newProgress,
                                progressString = newProgressString
                            )
                        }
                        //}
                    }
                    is PlayerState.Ready -> {
                        _state.value = state.value.copy(
                            duration = playerState.duration
                        )
                        //_uiState.value = HomeUiState.Ready
                    }
                }
            }
        }
    }

    private fun calculateProgressValue(progress: Long) : Float {
        return if (progress > 0) ((progress.toFloat() / state.value.duration.toFloat())  * 100f)
        else 0f
    }

    private fun formatDuration(duration: Long): String {
        val minute = duration / 60
        val seconds = duration % 60
        return String.format("%02d:%02d", minute, seconds)
    }

    private fun formatDurationFromMili(milliseconds: Long): String {
        val minute = milliseconds / 1000 / 60
        val seconds = milliseconds / 1000 % 60
        return String.format("%02d:%02d", minute, seconds)
    }

    fun onEvent(event: SongBarEvent) {
        when (event) {
            SongBarEvent.Backward -> TODO()
            SongBarEvent.Forward -> TODO()
            SongBarEvent.PlayPause -> {
                viewModelScope.launch {
                    playerEventListener.onEvent(PlayerEvent.PlayPause)
                }
            }
            is SongBarEvent.SeekTo -> TODO()
            is SongBarEvent.SelectAudio -> TODO()
            is SongBarEvent.ShowSnackbar -> TODO()
            SongBarEvent.SkipToNext -> TODO()
            SongBarEvent.SkipToPrevious -> TODO()
            SongBarEvent.Stop -> TODO()
            SongBarEvent.ToggleFullScreenMode -> {
                _state.value = state.value.copy(
                    isInFullScreenMode = !state.value.isInFullScreenMode
                )
            }
            is SongBarEvent.UpdateProgress -> TODO()
        }
    }
}