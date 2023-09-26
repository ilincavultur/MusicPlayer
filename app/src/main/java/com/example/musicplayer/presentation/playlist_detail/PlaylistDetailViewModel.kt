package com.example.musicplayer.presentation.playlist_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.core.util.Resource
import com.example.musicplayer.domain.exoplayer.PlayerEvent
import com.example.musicplayer.domain.exoplayer.PlayerEventListener
import com.example.musicplayer.domain.models.Playlist
import com.example.musicplayer.domain.models.PlaylistWithSongs
import com.example.musicplayer.domain.usecases.PlaylistUsecases
import com.example.musicplayer.presentation.home.HomeUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val playlistUsecases: PlaylistUsecases,
    private val playerEventListener: PlayerEventListener
) : ViewModel() {
    private val playlistWithSongsId: Int = checkNotNull(savedStateHandle["playlistWithSongsId"])

//    @OptIn(ExperimentalCoroutinesApi::class)
//    val playlistData = playlistWithSongsId.flatMap {
//
//    }

    private val _eventFlow = MutableSharedFlow<PlaylistDetailEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _state = mutableStateOf(PlaylistDetailState())
    val state: State<PlaylistDetailState> = _state

    private var searchJob: Job? = null

    init {
        loadSongs(playlistWithSongsId.toInt())
    }

    private fun loadSongs(id: Int) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500L)
            playlistUsecases.getPlaylist.invoke(id)
                .onEach { result ->
                    when (result) {
                        is Resource.Error -> {
                            _state.value = state.value.copy(
                                playlistWithSongs = result.data,
                                isLoading = false
                            )
                        }
                        is Resource.Loading -> {
                            _state.value = state.value.copy(
                                playlistWithSongs = result.data,
                                isLoading = true
                            )
                        }
                        is Resource.Success -> {
                            _state.value = state.value.copy(
                                playlistWithSongs = result.data,
                                isLoading = false
                            )
                        }
                    }
                }.launchIn(this)
        }
    }

    fun onEvent(event: PlaylistDetailEvent) {
        when (event) {
            PlaylistDetailEvent.Backward -> {
                viewModelScope.launch {
                    playerEventListener.onEvent(PlayerEvent.Backward)
                }
            }
            PlaylistDetailEvent.Forward -> {
                viewModelScope.launch {
                    playerEventListener.onEvent(PlayerEvent.Forward)
                }
            }
            PlaylistDetailEvent.PlayPause -> {
                viewModelScope.launch {
                    playerEventListener.onEvent(PlayerEvent.PlayPause)
                }
            }
            is PlaylistDetailEvent.SeekTo -> {
                viewModelScope.launch {
                    playerEventListener.onEvent(PlayerEvent.SeekTo(((state.value.duration * event.seekPos) / 100f).toLong()))
                }
            }
            is PlaylistDetailEvent.SelectAudio -> {
                viewModelScope.launch {
                    playerEventListener.onEvent(PlayerEvent.SelectAudio(event.selectedMediaIdx))
                }
            }
            is PlaylistDetailEvent.ShowSnackbar -> {
                // nada
            }
            PlaylistDetailEvent.Stop -> {
                viewModelScope.launch {
                    playerEventListener.onEvent(PlayerEvent.Stop)
                }
            }
            is PlaylistDetailEvent.UpdateProgress -> {
                viewModelScope.launch {
                    playerEventListener.onEvent(PlayerEvent.UpdateProgress(event.updatedProgress))
                    _state.value = state.value.copy(
                        progress = event.updatedProgress
                    )
                }
            }
            PlaylistDetailEvent.ToggleFullScreenMode -> {
                _state.value = state.value.copy(
                    isInFullScreenMode = !state.value.isInFullScreenMode
                )
            }
            PlaylistDetailEvent.SkipToNext -> {
                viewModelScope.launch {
                    playerEventListener.onEvent(PlayerEvent.SkipToNext)
                }
            }
            PlaylistDetailEvent.SkipToPrevious -> {
                viewModelScope.launch {
                    playerEventListener.onEvent(PlayerEvent.SkipToPrevious)
                }
            }
        }
    }
}