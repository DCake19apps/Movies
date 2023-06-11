package com.example.movies2

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.moveis_ui.HomeNowShowingState
import com.example.moveis_ui.HomeViewModel
import com.example.moveis_ui.HomeViewModelImpl
import com.example.movie_domain.MovieEntity
import com.example.movies2.ui.theme.MoviesTheme
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModelImpl = hiltViewModel(),
    onClickSeeAllNowShowing: () -> Unit = {}
) {
    val nowShowing by viewModel.nowShowingFlow.collectAsState()
    Column(modifier.verticalScroll(rememberScrollState())) {
        HomeSection(
            title = R.string.now_showing,
            state = nowShowing,
            onClickSeeAll = onClickSeeAllNowShowing
        )
    }
}

@Composable
fun HomeSection(
    @StringRes title: Int,
    state: HomeNowShowingState,
    modifier: Modifier = Modifier,
    onClickSeeAll: () -> Unit = {}
) {
    Column(modifier = modifier.fillMaxSize()) {
        Text(
            text = stringResource(title),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(8.dp)
        )
        when (state)  {
            is HomeNowShowingState.Ready -> {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    modifier = modifier
                ) {
                    items(state.movies.size) {
                        MoviePosterImageItem(state.movies[it])
                    }
                }
                TextButton(
                    onClick = onClickSeeAll,
                    modifier = Modifier.padding(2.dp).align(Alignment.End)
                ) {
                    Text(text = stringResource(id = R.string.see_all))
                }
            }

            HomeNowShowingState.Error -> {
                Text(
                    text = stringResource(id = R.string.error_loading),
                    modifier = Modifier.padding(32.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
            HomeNowShowingState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.padding(16.dp)
                        .height(60.dp)
                        .width(60.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Composable
fun MoviePosterImageItem(item: MovieEntity) {
    Surface(Modifier.size(width = 120.dp, height = 180.dp), RoundedCornerShape(4.dp)) {
        val painter = rememberAsyncImagePainter(item.posterPath)
//        val painter = rememberAsyncImagePainter(
//            ImageRequest.Builder(LocalContext.current).data(data = item.posterPath)
//                .apply(block = fun ImageRequest.Builder.() {
//                    crossfade(true)
//                }).build()
//        )
        Image(
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Preview
@Composable
fun HomeSectionPreview() {
    MoviesTheme {
        HomeSection(
            R.string.now_showing,
            HomeNowShowingState.Ready((0..4).map {
                MovieEntity(it,"","","","","") })
        )
    }
}


//@Preview
//@Composable
//fun HomeSectionPreview() {
//    MoviesTheme {
//        HomeSection(R.string.now_showing)
//    }
//}
