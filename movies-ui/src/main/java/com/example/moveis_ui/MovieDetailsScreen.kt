package com.example.moveis_ui

import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.moveis_ui.details.CastState
import com.example.moveis_ui.details.CrewState
import com.example.moveis_ui.details.DetailsState
import com.example.moveis_ui.details.DetailsViewModel
import com.example.moveis_ui.theme.ratingAverage
import com.example.moveis_ui.theme.ratingBad
import com.example.moveis_ui.theme.ratingDreadful
import com.example.moveis_ui.theme.ratingExcellent
import com.example.moveis_ui.theme.ratingGood
import com.example.moveis_ui.theme.ratingVeryBad
import com.example.moveis_ui.theme.ratingVeryGood
import com.example.movie_domain.details.MoviesDetailsEntity
import com.example.movie_domain.people.CastEntity
import com.example.movie_domain.people.CrewEntity
import com.example.movies2.PageLoading
import kotlinx.coroutines.launch

@Composable
fun MovieDetailsScreen(
    widthSizeClass: WindowWidthSizeClass,
    detailsViewModel: DetailsViewModel = hiltViewModel()
) {
    val overview by detailsViewModel.detailsFlow.collectAsState()
    val cast by detailsViewModel.castFlow.collectAsState()
    val crew by detailsViewModel.crewFlow.collectAsState()

    MovieDetailsScreen(widthSizeClass, overview, cast, crew)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieDetailsScreen(
    widthSizeClass: WindowWidthSizeClass,
    overview: DetailsState,
    cast: CastState, crew: CrewState
) {
    val pagerState = rememberPagerState(initialPage = 0, initialPageOffsetFraction = 0f) { 3 }
    val coroutineScope = rememberCoroutineScope()

    Column {
        Tabs(
            selectedTabIndex = pagerState.currentPage,
            onSelectedTab = { index: Int ->
                coroutineScope.launch { pagerState.animateScrollToPage(index) }
            }
        )
        HorizontalPager(
            state = pagerState
        ) { pageIndex ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                when (pageIndex) {
                    0 -> MovieOverview(widthSizeClass = widthSizeClass, overview)
                    1 -> MovieCast(widthSizeClass = widthSizeClass, cast)
                    2 -> MovieCrew(widthSizeClass = widthSizeClass, crew)
                }
            }
        }
    }
}

enum class TabPage(@StringRes val header: Int, val testTag: String) {
    OVERVIEW(R.string.overview, "tab_overview"),
    CAST(R.string.cast, "tab_cast"),
    CREW(R.string.crew, "tab_crew")
}

@Composable
fun Tabs(selectedTabIndex: Int, onSelectedTab: (index: Int) -> Unit) {
    TabRow(selectedTabIndex = selectedTabIndex) {
        TabPage.values().forEachIndexed { index, tabPage ->
            Tab(
                selected = index == selectedTabIndex,
                onClick = { onSelectedTab(index)},
                text = { Text(text = stringResource(id = tabPage.header)) },
                modifier = Modifier.testTag(tabPage.testTag)
            )
        }
    }
}

@Composable
fun MovieOverview(
    widthSizeClass: WindowWidthSizeClass,
    state: DetailsState,
    modifier: Modifier = Modifier
) {
    when (state) {
        DetailsState.Error -> {}
        DetailsState.Loading -> PageLoading()
        is DetailsState.Ready -> MovieOverview(widthSizeClass = widthSizeClass, details = state.details)
    }
}

@Composable
fun MovieCast(
    widthSizeClass: WindowWidthSizeClass,
    state: CastState,
    modifier: Modifier = Modifier
) {
    when (state) {
        CastState.Error -> {}
        CastState.Loading -> PageLoading()
        is CastState.Ready -> CastMembers(
            castMembers = state.cast,
            columns = if (widthSizeClass == WindowWidthSizeClass.Compact) 1 else 2
        )
    }
}

@Composable
fun MovieCrew(
    widthSizeClass: WindowWidthSizeClass,
    state: CrewState,
    modifier: Modifier = Modifier
) {
    when (state) {
        CrewState.Error -> {}
        CrewState.Loading -> PageLoading()
        is CrewState.Ready -> CrewMembers(
            crewMembers = state.crew,
            columns = if (widthSizeClass == WindowWidthSizeClass.Compact) 1 else 2
        )
    }
}

@Composable
fun MovieOverview(
    widthSizeClass: WindowWidthSizeClass,
    details: MoviesDetailsEntity,
    modifier: Modifier = Modifier
) {
    if (widthSizeClass == WindowWidthSizeClass.Compact) {
        Column(
            modifier = modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            MovieOverviewData(
                details = details,
                modifier = modifier
            )
            Text(
                text = details.overview,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .testTag("overview")
            )
        }
    } else {
        Row(
            modifier = modifier.padding(16.dp)
        ) {
            MovieOverviewData(details = details)
            Text(
                text = details.overview,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .testTag("overview")
            )
        }
    }
}

@Composable
fun MovieOverviewData(
    details: MoviesDetailsEntity,
    modifier: Modifier = Modifier
) {
    val ratingNum = if (details.rating.isBlank()) -1f else details.rating.toFloat()
    val colors =
        if (ratingNum < 0) Pair(Color.White, Color.White)
        else if (ratingNum >= 9) Pair(ratingExcellent, Color.Black)
        else if (ratingNum >= 8) Pair(ratingVeryGood, Color.Black)
        else if (ratingNum >= 7) Pair(ratingGood, Color.Black)
        else if (ratingNum >= 6) Pair(ratingAverage, Color.Black)
        else if (ratingNum >= 5) Pair(ratingBad, Color.Black)
        else if (ratingNum >= 4) Pair(ratingVeryBad, Color.White)
        else Pair(ratingDreadful, Color.White)
    Column {
        Row(modifier = modifier) {
            Surface(modifier, RoundedCornerShape(4.dp)) {
                val painter = rememberAsyncImagePainter(details.posterPath)
                Image(
                    painter = painter,
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(width = 120.dp, height = 180.dp)
                )
            }
            Column() {
                Text(
                    text = details.title,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .testTag("title")
                )
                Row(modifier = Modifier.padding(8.dp)) {
                    Text(
                        text = details.releaseDate,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.testTag("release_date")
                    )
                    Text(
                        text = stringResource(R.string.mins, details.runtime),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .padding(start = 32.dp)
                            .testTag("duration")
                    )
                }
                Score(
                    rating = details.rating,
                    color = colors.first,
                    textColor = colors.second,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
        Row(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(R.string.revenue, details.revenue),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.testTag("revenue")
            )
            Text(
                text = stringResource(R.string.budget, details.budget),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .padding(start = 32.dp)
                    .testTag("budget")
            )
        }
    }
}

@Composable
fun Score(
    rating: String,
    color: Color,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.size(60.dp,60.dp),
        RoundedCornerShape(30.dp),
        color = color
    ) {
        Box(modifier = Modifier
            .fillMaxSize(1.0f) // it will fill parent box
            .padding(8.dp)) {
            Text(
                text = rating,
                color = textColor,
                modifier = Modifier
                    .align(Alignment.Center)
                    .testTag("rating")
            )
        }
    }
}


@Composable
fun CastMembers(
    castMembers: List<CastEntity>,
    columns: Int,
    modifier: Modifier = Modifier
) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
        modifier = modifier.testTag("cast_members")
    ) {
        items(castMembers.size) {
            val credit = castMembers[it]
            Member(credit.name, credit.character, credit.profilePath, testId = it)
        }
    }

}
@Composable
fun CrewMembers(
    crewMembers: List<CrewEntity>,
    columns: Int,
    modifier: Modifier = Modifier
) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
        modifier = modifier
    ) {
        items(crewMembers.size) {
            val credit = crewMembers[it]
            Member(credit.name, credit.job, credit.profilePath, testId = it)
        }
    }
    
}
@Composable
fun Member(
    line1: String,
    line2: String,
    profilePath: String,
    modifier: Modifier = Modifier,
    testId: Int
) {
    Surface(
        modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Row(modifier = modifier.padding(4.dp)) {
            val painter = rememberAsyncImagePainter(profilePath)
            Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(88.dp)
                    .clip(CircleShape)
            )
            Column {
                Text(
                    text = line1,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .padding(8.dp)
                        .testTag("line_1_$testId")
                )
                Text(
                    text = line2,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .padding(8.dp)
                        .testTag("line_2_$testId")
                )
            }
        }
    }
}