package com.example.moveis_ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_domain.GetUpcomingMoviesUseCase
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
class UpcomingViewModel @Inject constructor(
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase
): ViewModel() {

    private val _seeAllUpcomingFlow = MutableStateFlow<SeeAllState>(SeeAllState.Loading)
    val seeAllUpcomingFlow: StateFlow<SeeAllState>
        get() = _seeAllUpcomingFlow

    init {
        Log.v("UpcomingViewModel", "init")
        viewModelScope.launch(CoroutineExceptionHandler {
                coroutineContext, throwable ->
            Log.v("UpcomingViewModel", "${throwable.message}")
            _seeAllUpcomingFlow.value = SeeAllState.Error
        }) {
            withContext(Dispatchers.Default) {
                val movies = getUpcomingMoviesUseCase.invoke()

                _seeAllUpcomingFlow.value = SeeAllState.Ready(movies)
            }
        }.invokeOnCompletion {
            if (it?.cause !is CancellationException) {
                _seeAllUpcomingFlow.value = SeeAllState.Error
           }
            Log.v("UpcomingViewModel", "completion: ${it?.message}")
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.v("UpcomingViewModel", "onCleared")
    }
}