package com.example.movies2

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
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.moveis_ui.details.CreditsViewModel
import com.example.moveis_ui.details.CrewState
import com.example.moveis_ui.details.DetailsState
import com.example.moveis_ui.details.DetailsViewModel
import com.example.movie_domain.details.MoviesDetailsEntity
import com.example.movie_domain.people.CastEntity
import com.example.movie_domain.people.CrewEntity
import com.example.movies2.ui.theme.ratingAverage
import com.example.movies2.ui.theme.ratingBad
import com.example.movies2.ui.theme.ratingDreadful
import com.example.movies2.ui.theme.ratingExcellent
import com.example.movies2.ui.theme.ratingGood
import com.example.movies2.ui.theme.ratingVeryBad
import com.example.movies2.ui.theme.ratingVeryGood
import kotlinx.coroutines.launch


@Composable
fun MovieDetailsScreen(
    detailsViewModel: DetailsViewModel = hiltViewModel(),
    creditsViewModel: CreditsViewModel = hiltViewModel()
) {
    val overview by detailsViewModel.detailsFlow.collectAsState()
    val cast by creditsViewModel.castFlow.collectAsState()
    val crew by creditsViewModel.crewFlow.collectAsState()

    MovieDetailsScreen(overview, cast, crew)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieDetailsScreen(overview: DetailsState, cast: CastState, crew: CrewState) {
    val pagerState = rememberPagerState(0)
    val coroutineScope = rememberCoroutineScope()

    Column {
        Tabs(
            selectedTabIndex = pagerState.currentPage,
            onSelectedTab = { index: Int ->
                coroutineScope.launch { pagerState.animateScrollToPage(index) }
            }
        )
        HorizontalPager(
            pageCount = 3,
            state = pagerState
        ) { pageIndex ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                when (pageIndex) {
                    0 -> MovieOverview(overview)
                    1 -> MovieCast(cast)
                    2 -> MovieCrew(crew)
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
fun MovieOverview(state: DetailsState, modifier: Modifier = Modifier) {
    when (state) {
        DetailsState.Error -> {}
        DetailsState.Loading -> PageLoading()
        is DetailsState.Ready -> MovieOverview(details = state.details)
    }
}

@Composable
fun MovieCast(state: CastState, modifier: Modifier = Modifier) {
    when (state) {
        CastState.Error -> {}
        CastState.Loading -> PageLoading()
        is CastState.Ready -> CastMembers(castMembers = state.cast)
    }
}

@Composable
fun MovieCrew(state: CrewState, modifier: Modifier = Modifier) {
    when (state) {
        CrewState.Error -> {}
        CrewState.Loading -> PageLoading()
        is CrewState.Ready -> CrewMembers(crewMembers = state.crew)
    }
}

@Composable
fun MovieOverview(details: MoviesDetailsEntity, modifier: Modifier = Modifier) {
    val ratingNum = details.rating.toFloat()
    val colors =
        if (ratingNum >= 9) Pair(ratingExcellent, Color.Black)
        else if (ratingNum >= 8) Pair(ratingVeryGood, Color.Black)
        else if (ratingNum >= 7) Pair(ratingGood, Color.Black)
        else if (ratingNum >= 6) Pair(ratingAverage, Color.Black)
        else if (ratingNum >= 5) Pair(ratingBad, Color.Black)
        else if (ratingNum >= 4) Pair(ratingVeryBad, Color.White)
        else Pair(ratingDreadful, Color.White)

    Column(
        modifier = modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(modifier = modifier.fillMaxWidth()) {
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
        Text(
            text = details.overview,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(start = 16.dp)
                .testTag("overview")
        )
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
fun CastMembers(castMembers: List<CastEntity>, modifier: Modifier = Modifier) {

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        modifier = modifier.testTag("cast_members")
    ) {
        items(castMembers.size) {
            val credit = castMembers[it]
            Member(credit.name, credit.character, credit.profilePath, testId = it)
        }
    }

}
@Composable
fun CrewMembers(crewMembers: List<CrewEntity>, modifier: Modifier = Modifier) {
    
    LazyColumn(              
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
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
        modifier.fillMaxWidth(), RoundedCornerShape(8.dp),
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