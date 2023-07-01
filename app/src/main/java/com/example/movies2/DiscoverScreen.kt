package com.example.movies2

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.primarySurface
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.moveis_ui.discover.DiscoverViewModel
import com.example.movie_domain.list.DiscoverFilter
import com.example.movie_domain.list.SortBy

@Composable
fun DiscoverScreen(
    modifier: Modifier = Modifier,
    discoverViewModel: DiscoverViewModel = hiltViewModel(),
    onClickItem: (id: Int) -> Unit = {}
) {
    val discoverResults by discoverViewModel.discoverFlow.collectAsState()

    var displayDialog by rememberSaveable { mutableStateOf(true) }

    Box(modifier = Modifier.fillMaxSize()) {
        if (displayDialog) {
            Dialog(
                properties = DialogProperties(),
                onDismissRequest = { displayDialog = false }
            ) {
                DiscoverDialog(modifier) {
                        sortBy: SortBy, minVoteAverage: Float, genres: List<Int> ->
                    discoverViewModel.discover(DiscoverFilter(sortBy, minVoteAverage, genres, null))
                    displayDialog = false
                }
            }
        }
        MoviesGridList(
            state = discoverResults,
            onClickRetry = { },
            onClickItem = onClickItem
        )
        FloatingActionButton(
            onClick = {
                displayDialog = true
            },
            containerColor = androidx.compose.material3.MaterialTheme.colorScheme.secondary,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .padding(16.dp)
                .align(alignment = Alignment.BottomEnd)
        ) {
            Icon(
                imageVector = Icons.Default.FilterList,
                contentDescription = "Filter",
                tint = Color.White,
            )
        }
    }
}

@Composable
fun DiscoverDialog(
    modifier: Modifier = Modifier,
    discover: (sortBy: SortBy, minVoteAverage: Float, genres: List<Int>) -> Unit
) {

    var sortBy = SortBy.values()[0]
    var minScore = 0f
    val selectedGenres = mutableSetOf<Genres>()

    Surface(modifier.fillMaxWidth(), RoundedCornerShape(4.dp)) {
        Column {
            SortBy(modifier = Modifier.align(Alignment.CenterHorizontally)) { index: Int -> sortBy = SortBy.values()[index] }
            MinimumScore { score: Float -> minScore = score }
            SelectGenres({ genres -> selectedGenres.add(genres) }, { genres -> selectedGenres.remove(genres) })
            TextButton(
                onClick = { discover(sortBy, minScore, selectedGenres.map { it.id }) },
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = stringResource(id = R.string.discover)
                )
            }
        }
    }
}

@Composable
fun SortBy(modifier: Modifier = Modifier, onChange: (index: Int) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val options = listOf(stringResource(R.string.vote_average),stringResource(R.string.popularity))
    var selected by remember { mutableStateOf(options[0]) }

    Text(
        text = stringResource(R.string.sort_by),
        style = androidx.compose.material3.MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(8.dp)
    )

    Column(modifier = modifier) {
        Button(
            modifier = Modifier
                .fillMaxWidth(fraction = 0.6f)
                .align(Alignment.CenterHorizontally),
            onClick = { expanded = !expanded }
        ) {
            Text(text = selected)
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = null,
            )
        }
        DropdownMenu(
            modifier = Modifier.align(Alignment.End),
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            options.forEachIndexed { index, s ->
                DropdownMenuItem(
                    onClick = {
                        onChange(index)
                        selected = options[index]
                        expanded = false
                    },
                    text = { Text(text = s) }
                )
            }
        }
    }
}

@Composable
fun MinimumScore(onChange: (score: Float) -> Unit) {
    var sliderPosition by remember { mutableStateOf(0f) }
    Text(
        text = stringResource(R.string.minimum_score),
        style = androidx.compose.material3.MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(8.dp)
    )
    Text(
        text = getDisplayScore(sliderPosition),
        style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
    Slider(
        modifier = Modifier.padding(horizontal = 16.dp),
        value = sliderPosition,
        onValueChange = {
            sliderPosition = it
            onChange(it*10)
        }
    )
}

private fun getDisplayScore(position: Float): String {
    return String.format("%.1f", position * 10)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SelectGenres(add: (genres: Genres) -> Unit, remove: (genres: Genres) -> Unit) {

    Text(
        text = stringResource(R.string.select_genres),
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(8.dp)
    )

    LazyHorizontalStaggeredGrid(
        rows = StaggeredGridCells.Fixed(4),
        contentPadding = PaddingValues(vertical = 4.dp, horizontal = 16.dp), 
        horizontalItemSpacing = 4.dp,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(176.dp)
    ) {
        items(Genres.values()) {
            GenreButton(text = it.genreName) { selected: Boolean ->
                if(selected) add(it) else remove(it)
            }
        }
    }

}

@Composable
fun GenreButton(text: String, onChange: (selected: Boolean) -> Unit) {
    var selected by remember { mutableStateOf(false) }
    val color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
    val textColor = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondary

    Button(
        onClick = {
            selected = !selected
            onChange(selected)
        },
        colors = ButtonDefaults.buttonColors(containerColor = color),
        modifier = Modifier.height(40.dp)
    ) {
        Text(
            text = text,
            color = textColor
        )
    }
}




