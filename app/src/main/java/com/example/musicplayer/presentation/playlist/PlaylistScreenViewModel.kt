package com.example.musicplayer.presentation.playlist

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.*
import androidx.core.net.toUri
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
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
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
            is PlaylistScreenEvent.DeletePlaylist -> {
                if ( state.value.contextMenuPlaylistId != null ) {
                    viewModelScope.launch {
                        playlistUsecases.deletePlaylist(state.value.contextMenuPlaylistId!!)
                        loadPlaylists()
                    }
                    _state.value = state.value.copy(
                        contextMenuPlaylistId = null
                    )
                }
            }
            is PlaylistScreenEvent.OnCardLongClick -> {
                _state.value = state.value.copy(
                    contextMenuPlaylistId = event.playlistId
                )
            }
            PlaylistScreenEvent.OnDismissSheet -> {
                _state.value = state.value.copy(
                    contextMenuPlaylistId = null
                )
            }
            is PlaylistScreenEvent.SetCoverPhoto -> {

                val playlistToUpdate = state.value.playlists.first {
                    it.playlist.playlistId == state.value.contextMenuPlaylistId
                }.playlist

                val newPlaylist = Playlist(
                    playlistId = playlistToUpdate.playlistId,
                    playlistName = playlistToUpdate.playlistName,
                    playlistCoverPhoto = event.playlistCoverPhoto
                )

                playlistToUpdate.let {
                    viewModelScope.launch {
                        playlistUsecases.chooseCoverPhoto(newPlaylist)
                        loadPlaylists()
                    }
                }

            }
        }
    }

}