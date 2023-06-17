package com.example.moveis_ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
): ViewModel() {

    init {
        val ssh = savedStateHandle
        val id = savedStateHandle.get<String>("movieId")
        println(id)
    }
}