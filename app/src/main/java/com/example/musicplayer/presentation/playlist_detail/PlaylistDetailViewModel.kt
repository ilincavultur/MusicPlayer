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
    private var eventListenerJob: Job? = null

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
//        eventListenerJob?.cancel()
//        eventListenerJob = viewModelScope.launch {
//            delay(500L)
//            playerEventListener.state.collectLatest { playerState ->
//                when (playerState) {
//                    is PlayerState.Buffering -> {
//                        viewModelScope.launch {
//                            val newProgress = playerState.progress
//                            _state.value = state.value.copy(
//                                progress = newProgress.toFloat(),
//                                progressString = formatDurationFromMili(newProgress.toLong())
//                            )
//                        }
//                    }
//                    is PlayerState.CurrentlyPlaying -> {
//                        _state.value = state.value.copy(
//                            currentlySelectedSong = state.value.songs[playerState.mediaItemIdx],
//                            currentlySelectedSongString = formatDurationFromMili(state.value.duration)
//                        )
//                    }
//                    is PlayerState.Ended -> TODO()
//                    is PlayerState.Idle -> TODO()
//                    PlayerState.Initial -> {
//                        _uiState.value = PlaylistUiState.Initial
//                    }
//                    is PlayerState.Playing -> {
//                        _state.value = state.value.copy(
//                            isPlaying = playerState.isPlaying
//                        )
//                    }
//                    is PlayerState.Progress -> {
//                        viewModelScope.launch {
//                            val newProgress = calculateProgressValue(playerState.progress)
//                            val newProgressString = formatDuration(state.value.progress.toLong())
//                            withContext(Dispatchers.Main) {
//                                _state.value = state.value.copy(
//                                    progress = newProgress,
//                                    progressString = newProgressString
//                                )
//                            }
//                        }
//                    }
//                    is PlayerState.Ready -> {
//                        _state.value = state.value.copy(
//                            duration = playerState.duration
//                        )
//                        _uiState.value = PlaylistUiState.Ready
//                    }
//                }
//            }
//        }
    }

    init {
        loadAllSongs()
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
//                            if (state.value.playlistWithSongs.songs.isNotEmpty()) {
//                                setMediaItems()
//                            }
                        }
                    }
                }.launchIn(this)
        }
    }

    private fun setMediaItems() {
        state.value.playlistWithSongs.songs.map { song ->
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
            it.forEach {
                println("mediaItem " + it.mediaMetadata.albumArtist)
            }
            playerEventListener.setMediaItems(it)
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
                    playerEventListener.onEvent(PlayerEvent.SelectAudio(event.selectedMediaIdx, event.mediaId))
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