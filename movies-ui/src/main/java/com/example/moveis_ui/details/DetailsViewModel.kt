package com.example.moveis_ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_domain.details.GetMoviesDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.cancellation.CancellationException
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMoviesDetailsUseCase: GetMoviesDetailsUseCase,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
): ViewModel() {

    private val _detailsFlow = MutableStateFlow<DetailsState>(DetailsState.Loading)
    val detailsFlow: StateFlow<DetailsState>
        get() = _detailsFlow

    init {
        savedStateHandle.get<String>("movieId")?.toInt()?.let { id ->
            viewModelScope.launch(
                CoroutineExceptionHandler { coroutineContext, throwable ->
                    _detailsFlow.value = DetailsState.Error
                }
            ) {
                withContext(defaultDispatcher) {
                    val details = getMoviesDetailsUseCase.invoke(id)
                    _detailsFlow.value = DetailsState.Ready(details)
                }
            }.invokeOnCompletion {
                if (it != null && it.cause !is CancellationException) {
                    _detailsFlow.value = DetailsState.Error
                }
            }
        }
    }
}