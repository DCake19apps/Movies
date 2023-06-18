package com.example.movies2

import android.util.Log
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.moveis_ui.seeall.UpcomingViewModel
import com.example.movies2.ui.theme.MoviesTheme
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import com.example.moveis_ui.MovieListState

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.ui.res.stringResource
import com.example.movie_domain.list.MovieEntity
import androidx.compose.ui.platform.LocalConfiguration
import com.example.moveis_ui.seeall.NowPlayingViewModel
import com.example.moveis_ui.seeall.PopularViewModel
import com.example.moveis_ui.seeall.TopRatedViewModel

@Composable
fun SeeAllNowPlayingScreen(
    modifier: Modifier = Modifier,
    viewModel: NowPlayingViewModel = hiltViewModel(),
    onClickItem: (id: Int) -> Unit = {}
) {
    val nowPlaying by viewModel.seeAllNowPlayingFlow.collectAsState()
    MoviesGridList(
        state = nowPlaying,
        onClickRetry = { viewModel.initialize() },
        onClickItem = onClickItem
    )
}
@Composable
fun SeeAllUpcomingScreen(
    modifier: Modifier = Modifier,
    viewModel: UpcomingViewModel = hiltViewModel(),
    onClickItem: (id: Int) -> Unit = {}
) {
    val upcoming by viewModel.seeAllUpcomingFlow.collectAsState()
    MoviesGridList(
        state = upcoming,
        onClickRetry = { viewModel.initialize() },
        onClickItem = onClickItem
    )
}
@Composable
fun SeeAllTopRatedScreen(
    modifier: Modifier = Modifier,
    viewModel: TopRatedViewModel = hiltViewModel(),
    onClickItem: (id: Int) -> Unit = {}
) {
    val topRated by viewModel.seeAllTopRatedFlow.collectAsState()
    MoviesGridList(
        state = topRated,
        onClickRetry = { viewModel.initialize() },
        onClickItem = onClickItem
    )
}
@Composable
fun SeeAllPopularScreen(
    modifier: Modifier = Modifier,
    viewModel: PopularViewModel = hiltViewModel(),
    onClickItem: (id: Int) -> Unit = {}
) {
    val popular by viewModel.seeAllPopularFlow.collectAsState()
    MoviesGridList(
        state = popular,
        onClickRetry = { viewModel.initialize() },
        onClickItem = onClickItem
    )
}
@Composable
fun MoviesGridList(
    state: MovieListState,
    onClickRetry: () -> Unit = {},
    onClickItem: (id: Int) -> Unit = {}
) {

    when (state) {
        MovieListState.Error -> {
            ShowAllError(onClickRetry = onClickRetry)
            Log.v("Upcoming","error")
        }
        MovieListState.Loading -> {
            ShowAllLoading()
        }
        is MovieListState.Ready -> {
            ShowAllMovies(state.movies, onClickItem = onClickItem)
        }
    }
}

@Composable
fun ShowAllLoading(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier
                .padding(16.dp)
                .height(60.dp)
                .width(60.dp)
                .align(Alignment.TopCenter)
        )
    }
}

@Composable
fun ShowAllError(modifier: Modifier = Modifier, onClickRetry: () -> Unit = {}) {
    Column(modifier = modifier.fillMaxSize()) {
        Text(
            text = stringResource(R.string.error_loading),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally)
        )
        Spacer(Modifier.height(8.dp))
        TextButton(
            onClick = onClickRetry,
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(text = stringResource(id = R.string.retry))
        }
    }
}

@Composable
fun ShowAllMovies(
    movies: List<MovieEntity>,
    modifier: Modifier = Modifier,
    onClickItem: (id: Int) -> Unit = {}
) {
    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    LazyVerticalGrid(
        columns = GridCells.Adaptive(160.dp),
        contentPadding = PaddingValues(vertical = 16.dp, horizontal = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        items(movies) {item ->
            MoviePosterImageItem(
                item = item,
                Modifier.size(width = 200.dp, height = 300.dp),
                onClickItem
            )
        }
    }
}

@Preview
@Composable
fun FullListPreview() {
    MoviesTheme {
        SeeAllUpcomingScreen()
    }
}