package com.example.musicplayer.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.core.util.Resource
import com.example.musicplayer.domain.usecases.GetSongsUsecase
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
class HomeViewModel @Inject constructor(
    private val songsUsecase: GetSongsUsecase
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<HomeUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

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
                        }
                    }
                }.launchIn(this)
        }
    }
}