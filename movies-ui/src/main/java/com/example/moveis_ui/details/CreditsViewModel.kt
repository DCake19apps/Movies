package com.example.moveis_ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_domain.people.GetCreditsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.cancellation.CancellationException
import javax.inject.Inject

@HiltViewModel
class CreditsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getCreditsUseCase: GetCreditsUseCase
): ViewModel() {

    private val _castFlow = MutableStateFlow<CastState>(CastState.Loading)
    val castFlow: StateFlow<CastState>
        get() = _castFlow

    private val _crewFlow = MutableStateFlow<CrewState>(CrewState.Loading)
    val crewFlow: StateFlow<CrewState>
        get() = _crewFlow

    init {
        savedStateHandle.get<String>("movieId")?.toInt()?.let { id ->

            viewModelScope.launch(CoroutineExceptionHandler { coroutineContext, throwable ->
                _castFlow.value = CastState.Error
                _crewFlow.value = CrewState.Error
            }) {
                withContext(Dispatchers.Default) {
                    val details = getCreditsUseCase.invoke(id)
                    _castFlow.value = CastState.Ready(details.cast)
                    _crewFlow.value = CrewState.Ready(details.crew)
                }
            }.invokeOnCompletion {
                if (it != null && it.cause !is CancellationException) {
                    _castFlow.value = CastState.Error
                    _crewFlow.value = CrewState.Error
                }
            }
        }
    }
}