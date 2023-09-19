package com.example.musicplayer.presentation.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import com.example.musicplayer.core.util.Resource
import com.example.musicplayer.domain.exoplayer.PlayerEvent
import com.example.musicplayer.domain.exoplayer.PlayerEventListener
import com.example.musicplayer.domain.exoplayer.PlayerState
import com.example.musicplayer.domain.usecases.GetSongsUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val songsUsecase: GetSongsUsecase,
    private val playerEventListener: PlayerEventListener
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
                        calculateProgressValue(playerState.progress)
                    }
                    is PlayerState.CurrentlyPlaying -> {
                        _state.value = state.value.copy(
                            currentlySelectedSong = state.value.songs[playerState.mediaItemIdx]
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
                        calculateProgressValue(playerState.progress)
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

    private fun calculateProgressValue(progress: Long) {
        _state.value = state.value.copy(
            progress = if (progress > 0) ((progress.toFloat() / state.value.duration.toFloat()) * 100f)
            else 0f
        )

        _state.value = state.value.copy(
            progressString = formatDuration(progress)
        )
    }

    private fun formatDuration(duration: Long): String {
        val minute = TimeUnit.MINUTES.convert(duration, TimeUnit.MILLISECONDS)
        val seconds = (minute) - minute * TimeUnit.SECONDS.convert(1, TimeUnit.MINUTES)
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
            HomeUiEvent.Backward -> {
                viewModelScope.launch {
                    playerEventListener.onEvent(PlayerEvent.Backward)
                }
            }
            HomeUiEvent.Forward -> {
                viewModelScope.launch {
                    playerEventListener.onEvent(PlayerEvent.Forward)
                }
            }
            HomeUiEvent.PlayPause -> {
                viewModelScope.launch {
                    playerEventListener.onEvent(PlayerEvent.PlayPause)
                }
            }
            is HomeUiEvent.SeekTo -> {
                viewModelScope.launch {
                    playerEventListener.onEvent(PlayerEvent.SeekTo(((state.value.duration * event.seekPos) / 100f).toLong()))
                }
            }
            is HomeUiEvent.SelectAudio -> {
                viewModelScope.launch {
                    playerEventListener.onEvent(PlayerEvent.SelectAudio(event.selectedMediaIdx))
                }
            }
            is HomeUiEvent.ShowSnackbar -> {
                // nada
            }
            HomeUiEvent.Stop -> {
                viewModelScope.launch {
                    playerEventListener.onEvent(PlayerEvent.Stop)
                }
            }
            is HomeUiEvent.UpdateProgress -> {
                viewModelScope.launch {
                    playerEventListener.onEvent(PlayerEvent.UpdateProgress(event.updatedProgress))
                    _state.value = state.value.copy(
                        progress = event.updatedProgress
                    )
                }
            }
        }
    }
}