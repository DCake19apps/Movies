package com.example.moveis_ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_domain.GetNowShowingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModelImpl @Inject constructor(
    private val getNowShowingMoviesUseCase: GetNowShowingMoviesUseCase
): ViewModel(), HomeViewModel {
    private val _nowShowingFlow = MutableStateFlow<HomeNowShowingState>(HomeNowShowingState.Loading)
    override val nowShowingFlow: StateFlow<HomeNowShowingState>
        get() = _nowShowingFlow

    init {
        Log.v("HomeViewModel", "init")
        viewModelScope.launch(CoroutineExceptionHandler {
                coroutineContext, throwable ->
            _nowShowingFlow.value = HomeNowShowingState.Error
        }) {
            withContext(Dispatchers.Default) {
                val movies = getNowShowingMoviesUseCase.invoke()
                _nowShowingFlow.value = HomeNowShowingState.Ready(movies)
            }
        }
    }
}