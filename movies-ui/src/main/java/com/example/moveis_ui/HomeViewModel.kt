package com.example.moveis_ui

import kotlinx.coroutines.flow.StateFlow

interface HomeViewModel {
    val nowShowingFlow: StateFlow<HomeNowShowingState>
}