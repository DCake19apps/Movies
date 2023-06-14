package com.example.moveis_ui.seeall

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moveis_ui.SeeAllState
import com.example.movie_domain.GetNowPlayingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class NowPlayingViewModel@Inject constructor(
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase
): ViewModel() {

    private val _seeAllNowPlayingFlow = MutableStateFlow<SeeAllState>(SeeAllState.Loading)
    val seeAllNowPlayingFlow: StateFlow<SeeAllState>
        get() = _seeAllNowPlayingFlow

    init {
        initialize()
    }

    fun initialize() {
        viewModelScope.launch(CoroutineExceptionHandler {
                coroutineContext, throwable ->
            _seeAllNowPlayingFlow.value = SeeAllState.Error
        }) {
            withContext(Dispatchers.Default) {
                val movies = getNowPlayingMoviesUseCase.invoke()

                _seeAllNowPlayingFlow.value = SeeAllState.Ready(movies)
            }
        }.invokeOnCompletion {
            if (it !=null && it.cause !is CancellationException) {
                _seeAllNowPlayingFlow.value = SeeAllState.Error
            }
        }
    }
}
