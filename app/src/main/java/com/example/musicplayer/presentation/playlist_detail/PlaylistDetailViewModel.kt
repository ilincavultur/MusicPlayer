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
import com.example.musicplayer.domain.usecases.SongUsecases
import com.google.protobuf.MapEntryLite
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val playlistUsecases: PlaylistUsecases,
    private val songsUsecase: SongUsecases,
    private val playerEventListener: PlayerEventListener
) : ViewModel() {

    private val _state = mutableStateOf(PlaylistDetailState())
    val state: State<PlaylistDetailState> = _state

    private val _eventFlow = MutableSharedFlow<PlaylistDetailEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var searchPlaylistSongs: Job? = null
    private var searchAllSongs: Job? = null

    init {
        savedStateHandle.get<Int>("playlistWithSongsId")?.let { playlistId ->
            println("playlistWithSongsId: " + playlistId)
            if(playlistId != -1) {
//                viewModelScope.launch {
//                    playlistUsecases.getPlaylistFlowUsecase(playlistId)?.also { playlist ->
//                        playlistWithSongsId = playlist.playlist.playlistId
//                        _state.value = state.value.copy(
//                            playlistWithSongs = playlist
//                        )
//                    }
//                }
                loadPlaylistSongs(playlistId)
            }
        }
    }

    init {
        loadAllSongs()
    }

    private fun loadPlaylistSongs(id: Int) {
        searchPlaylistSongs?.cancel()
        searchPlaylistSongs = viewModelScope.launch {
            delay(500L)
            playlistUsecases.getPlaylist.invoke(id)
                .onEach { result ->
                    when (result) {
                        is Resource.Error -> {
                            _state.value = state.value.copy(
                                playlistWithSongs = result.data ?: PlaylistWithSongs(Playlist(), emptyList()),
                                isLoading = false
                            )
                        }
                        is Resource.Loading -> {
                            _state.value = state.value.copy(
                                playlistWithSongs = result.data ?: PlaylistWithSongs(Playlist(), emptyList()),
                                isLoading = true
                            )
                        }
                        is Resource.Success -> {
                            _state.value = state.value.copy(
                                playlistWithSongs = result.data ?: PlaylistWithSongs(Playlist(), emptyList()),
                                isLoading = false
                            )
                        }
                    }
                }.launchIn(this)
        }
    }

    private fun loadAllSongs() {
        searchAllSongs?.cancel()
        searchAllSongs = viewModelScope.launch {
            delay(500L)
            songsUsecase.getSongs.invoke()
                .onEach { result ->
                    when (result) {
                        is Resource.Error -> {
                            _state.value = state.value.copy(
                                songs = result.data ?: emptyList(),
                                isLoading = false
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
            PlaylistDetailEvent.ToggleSelectSongsDialog -> {
                _state.value = state.value.copy(
                    isSelectSongsDialogOpen = !state.value.isSelectSongsDialogOpen
                )
            }
            is PlaylistDetailEvent.CheckSong -> {
                if (state.value.checkedSongs[event.songId] != null) {
                    _state.value = state.value.copy(
                        checkedSongs = state.value.checkedSongs.minus(event.songId)
                    )
                } else {
                    _state.value = state.value.copy(
                        checkedSongs = state.value.checkedSongs.plus(Pair(event.songId, true))
                    )
                }
            }
            PlaylistDetailEvent.OnCancelSelectSongsClick -> {
                _state.value = state.value.copy(
                    checkedSongs = emptyMap(),
                    isSelectSongsDialogOpen = !state.value.isSelectSongsDialogOpen
                )
            }
        }
    }
}