package com.example.movies2

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.moveis_ui.details.CastState
import com.example.moveis_ui.details.CreditsViewModel
import com.example.moveis_ui.details.CrewState
import com.example.moveis_ui.details.DetailsState
import com.example.moveis_ui.details.DetailsViewModel
import com.example.movie_domain.people.CastEntity
import com.example.movie_domain.people.CrewEntity

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieDetailsScreen(
    detailsViewModel: DetailsViewModel = hiltViewModel(),
    creditsViewModel: CreditsViewModel = hiltViewModel()
) {
    val overview by detailsViewModel.detailsFlow.collectAsState()
    val cast by creditsViewModel.castFlow.collectAsState()
    val crew by creditsViewModel.crewFlow.collectAsState()
    
    Column() {
        Text(
            text = "Movie Title",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp)
        )
        HorizontalPager(
            pageCount = 3
        ) { pageIndex ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
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

@Composable
fun MovieOverview(state: DetailsState) {
    when (state) {
        DetailsState.Error -> {}
        DetailsState.Loading -> PageLoading()
        is DetailsState.Ready -> {}
    }
    Text(text = "Overview", style = MaterialTheme.typography.headlineMedium)
}

@Composable
fun MovieCast(state: CastState) {
    when (state) {
        CastState.Error -> {}
        CastState.Loading -> PageLoading()
        is CastState.Ready -> CastMembers(castMembers = state.crew)
    }
}

@Composable
fun MovieCrew(state: CrewState) {
    when (state) {
        CrewState.Error -> {}
        CrewState.Loading -> PageLoading()
        is CrewState.Ready -> CrewMembers(crewMembers = state.crew)
    }
}
@Composable
fun CastMembers(castMembers: List<CastEntity>, modifier: Modifier = Modifier) {

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = modifier
    ) {
        items(castMembers.size) {
            val credit = castMembers[it]
            Member(credit.name, credit.character, credit.profilePath)
        }
    }

}
@Composable
fun CrewMembers(crewMembers: List<CrewEntity>, modifier: Modifier = Modifier) {
    
    LazyColumn(              
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = modifier
    ) {
        items(crewMembers.size) {
            val credit = crewMembers[it]
            Member(credit.name, credit.job, credit.profilePath)
        }
    }
    
}
@Composable
fun Member(
    line1: String,
    line2: String,
    profilePath: String,
    modifier: Modifier = Modifier
) {
    Surface(modifier.fillMaxWidth(), RoundedCornerShape(8.dp), color = Color.LightGray) {
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
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(8.dp)
                )
                Text(
                    text = line2,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}
