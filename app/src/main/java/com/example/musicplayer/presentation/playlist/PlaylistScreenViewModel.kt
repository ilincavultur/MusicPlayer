package com.example.musicplayer.presentation.playlist

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.core.util.Resource
import com.example.musicplayer.domain.models.Playlist
import com.example.musicplayer.domain.usecases.PlaylistUsecases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistScreenViewModel @Inject constructor(
    private val playlistUsecases: PlaylistUsecases,
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<PlaylistScreenEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _state = mutableStateOf(PlaylistScreenState())
    val state: State<PlaylistScreenState> = _state

    @OptIn(ExperimentalCoroutinesApi::class)
    val userNameHasError: StateFlow<Boolean> =
        snapshotFlow { state.value.dialogText }
            .mapLatest {
                it.isEmpty() || it.isBlank()
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = false
            )

    private var searchJob: Job? = null

    init {
        loadPlaylists()
    }

    private fun loadPlaylists() {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500L)
            playlistUsecases.getPlaylists.invoke()
                .onEach { result ->
                    when (result) {
                        is Resource.Error -> {
                            _state.value = state.value.copy(
                                playlists = result.data ?: emptyList(),
                                isLoading = false
                            )
                            _eventFlow.emit(
                                PlaylistScreenEvent.ShowSnackbar(
                                    result.message ?: "Unknown Error"
                                )
                            )
                        }
                        is Resource.Loading -> {
                            _state.value = state.value.copy(
                                playlists = result.data ?: emptyList(),
                                isLoading = true
                            )
                        }
                        is Resource.Success -> {
                            _state.value = state.value.copy(
                                playlists = result.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                    }
                }.launchIn(this)
        }
    }

    fun onEvent(event: PlaylistScreenEvent) {
        when(event) {
            is PlaylistScreenEvent.ShowSnackbar -> TODO()
            is PlaylistScreenEvent.CreatePlaylist -> {
                viewModelScope.launch {
                    playlistUsecases.createPlaylist(
                        playlist = Playlist(playlistName = state.value.dialogText),
                        songs = emptyList()
                    )
                    loadPlaylists()
                }
                _state.value = state.value.copy(
                    isCreateDialogOpen = !state.value.isCreateDialogOpen,
                    dialogText = ""
                )
            }
            is PlaylistScreenEvent.AddSongsToPlaylist -> {
                viewModelScope.launch {
                    playlistUsecases.addSongsToPlaylist(playlist = event.playlist, songs = event.songs)
                    loadPlaylists()
                }

            }
            PlaylistScreenEvent.ToggleCreateDialog -> {
                _state.value = state.value.copy(
                    isCreateDialogOpen = !state.value.isCreateDialogOpen
                )
            }
            is PlaylistScreenEvent.UpdateDialogText -> {
                _state.value = state.value.copy(
                    dialogText = event.playlistName
                )
            }
        }
    }
}