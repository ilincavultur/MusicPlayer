package com.example.musicplayer.presentation.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.session.MediaController
import com.example.musicplayer.core.util.Resource
import com.example.musicplayer.domain.exoplayer.PlayerEvent
import com.example.musicplayer.domain.exoplayer.PlayerEventListener
import com.example.musicplayer.domain.exoplayer.PlayerState
import com.example.musicplayer.domain.usecases.GetSongsUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val songsUsecase: GetSongsUsecase,
    private val playerEventListener: PlayerEventListener,
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<HomeUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Initial)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private var searchJob: Job? = null

    init {
        loadSongs()
    }

    init {
        viewModelScope.launch {
            playerEventListener.state.collectLatest { playerState ->
                when (playerState) {
                    is PlayerState.Buffering -> {
                        val newProgress = playerState.progress
                        _state.value = state.value.copy(
                            progress = newProgress.toFloat(),
                            progressString = formatDurationFromMili(newProgress.toLong())
                        )
                    }
                    is PlayerState.CurrentlyPlaying -> {
                        println("event listener currently playing idx " + playerState.mediaItemIdx)
                        _state.value = state.value.copy(
                            currentlySelectedSong = state.value.songs[playerState.mediaItemIdx],
                            currentlySelectedSongString = formatDurationFromMili(state.value.duration)
                        )
                    }
                    is PlayerState.Ended -> TODO()
                    is PlayerState.Idle -> TODO()
                    PlayerState.Initial -> {
                        _uiState.value = HomeUiState.Initial
                    }
                    is PlayerState.Playing -> {
                        _state.value = state.value.copy(
                            isPlaying = playerState.isPlaying
                        )
                    }
                    is PlayerState.Progress -> {
                        val newProgress = calculateProgressValue(playerState.progress)
                        val newProgressString = formatDuration(state.value.progress.toLong())
                        withContext(Dispatchers.Main) {
                            _state.value = state.value.copy(
                                progress = newProgress,
                                progressString = newProgressString
                            )
                        }
                    }
                    is PlayerState.Ready -> {
                        _state.value = state.value.copy(
                            duration = playerState.duration
                        )
                        _uiState.value = HomeUiState.Ready
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
                                isLoading = false
                            )
                            _eventFlow.emit(
                                HomeUiEvent.ShowSnackbar(
                                    result.message ?: "Unknown Error"
                                )
                            )
                        }
                        is Resource.Loading -> {
                            _state.value = state.value.copy(
                                songs = result.data ?: emptyList(),
                                isLoading = true
                            )
                        }
                        is Resource.Success -> {
                            _state.value = state.value.copy(
                                songs = result.data ?: emptyList(),
                                isLoading = false
                            )
                            setMediaItems()
                        }
                    }
                }.launchIn(this)
        }
    }

    private fun setMediaItems() {
        state.value.songs.map { song ->
            MediaItem.Builder()
                .setUri(song.songUrl)
                .setMediaId(song.mediaId.toString())
                .setMediaMetadata(
                    MediaMetadata.Builder()
                        .setAlbumArtist(song.artist)
                        .setDisplayTitle(song.title)
                        .setTitle(song.artist)
                        .build()
                ).build()
        }.also {
            playerEventListener.setMediaItems(it)
        }
    }

    fun onEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.SelectAudio -> {
                viewModelScope.launch {
                    playerEventListener.onEvent(PlayerEvent.SelectAudio(event.selectedMediaIdx, event.mediaId))
                }
            }
            is HomeUiEvent.ShowSnackbar -> {
                // nada
            }
        }
    }
}