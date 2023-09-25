package com.example.musicplayer.presentation.playlist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.core.util.Resource
import com.example.musicplayer.domain.models.Playlist
import com.example.musicplayer.domain.usecases.GetSongsUsecase
import com.example.musicplayer.domain.usecases.PlaylistUsecases
import com.example.musicplayer.presentation.home.HomeState
import com.example.musicplayer.presentation.home.HomeUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
                    playlistUsecases.createPlaylist(playlist = Playlist(playlistName = event.playlistName), songs = emptyList())
                    loadPlaylists()
                }

            }
            is PlaylistScreenEvent.AddSongsToPlaylist -> {
                viewModelScope.launch {
                    playlistUsecases.addSongsToPlaylist(playlist = event.playlist, songs = event.songs)
                    loadPlaylists()
                }

            }
        }
    }
}