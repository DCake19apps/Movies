package com.example.movies2

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.moveis_ui.UpcomingViewModel
import com.example.movies2.ui.theme.MoviesTheme
import androidx.compose.runtime.getValue
import com.example.moveis_ui.SeeAllState

@Composable
fun SeeAllScreen(
    modifier: Modifier = Modifier,
    viewModel: UpcomingViewModel = hiltViewModel(),
) {
    val upcoming by viewModel.seeAllUpcomingFlow.collectAsState()
    MoviesGridList(state = upcoming)
    
}

@Composable
fun MoviesGridList(state: SeeAllState) {
    Text(text = "See all")
    when (state) {
        SeeAllState.Error -> {
            Log.v("Upcoming","error")
        }
        SeeAllState.Loading -> {
            Log.v("Upcoming","loading")
        }
        is SeeAllState.Ready -> {
            state.movies.take(3).forEach {
                Log.v("Upcoming", it.title)
            }
        }
    }
}

@Preview
@Composable
fun FullListPreview() {
    MoviesTheme {
        SeeAllScreen()
    }
}