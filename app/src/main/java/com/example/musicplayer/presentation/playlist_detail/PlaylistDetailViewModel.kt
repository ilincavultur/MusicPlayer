package com.example.musicplayer.presentation.playlist_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import com.example.musicplayer.core.util.Resource
import com.example.musicplayer.domain.exoplayer.PlayerEvent
import com.example.musicplayer.domain.exoplayer.PlayerEventListener
import com.example.musicplayer.domain.exoplayer.PlayerState
import com.example.musicplayer.domain.models.Playlist
import com.example.musicplayer.domain.models.PlaylistWithSongs
import com.example.musicplayer.domain.usecases.PlaylistUsecases
import com.example.musicplayer.domain.usecases.SongUsecases
import com.example.musicplayer.presentation.home.HomeUiState
import com.google.protobuf.MapEntryLite
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
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

    private val _uiState = MutableStateFlow<PlaylistUiState>(PlaylistUiState.Initial)
    val uiState: StateFlow<PlaylistUiState> = _uiState.asStateFlow()

    private var searchPlaylistSongs: Job? = null
    private var searchAllSongs: Job? = null

    init {
        savedStateHandle.get<Int>("playlistWithSongsId")?.let { playlistId ->
            println("playlistWithSongsId: " + playlistId)
            if(playlistId != -1) {
                _state.value = state.value.copy(
                    currentPlaylistId = playlistId
                )
                loadPlaylistSongs(playlistId)
            }
        }
    }

    init {
        loadAllSongs()

        viewModelScope.launch {
            playerEventListener.state.collectLatest { playerState ->
                when (playerState) {
                    is PlayerState.CurrentlyPlaying -> {
                        println("event listener currently playing idx " + playerState.mediaItemIdx)
                        _state.value = state.value.copy(
                            currentlySelectedSong = state.value.songs[playerState.mediaItemIdx],
                        )
                    }
                    is PlayerState.Playing -> {
                        _state.value = state.value.copy(
                            isPlaying = playerState.isPlaying
                        )
                    }
                    else -> {

                    }
                }
            }
        }
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
                                isLoading = false,
                                isPlaying = playerEventListener.exoPlayer.isPlaying,
                            )

                            result.data?.songs?.forEach {
                                if (it.mediaId == playerEventListener.exoPlayer.currentMediaItem?.mediaId) {
                                    _state.value = state.value.copy(
                                        currentlySelectedSong = it
                                    )
                                }
                            }

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
            is PlaylistDetailEvent.SelectAudio -> {
                viewModelScope.launch {
                    playerEventListener.onEvent(PlayerEvent.SelectAudio(event.selectedMediaIdx, event.mediaId))
                }
            }
            is PlaylistDetailEvent.ShowSnackbar -> {
                // nada
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
            PlaylistDetailEvent.AddToPlaylist -> {
                val ids = state.value.checkedSongs.keys.toList()
                viewModelScope.launch {
                    playlistUsecases.addSongsToPlaylist(
                        state.value.playlistWithSongs.playlist,
                        state.value.songs.filter {
                            ids.contains(it.mediaId?.toInt())
                        }
                    )
                    state.value.playlistWithSongs.playlist.playlistId?.let { loadPlaylistSongs(it) }
                }
                _state.value = state.value.copy(
                    checkedSongs = emptyMap(),
                    isSelectSongsDialogOpen = !state.value.isSelectSongsDialogOpen
                )
            }
        }
    }
}