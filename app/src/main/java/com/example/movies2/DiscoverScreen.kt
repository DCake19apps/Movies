package com.example.movies2

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.moveis_ui.discover.DiscoverViewModel

@Composable
fun DiscoverScreen(
    modifier: Modifier = Modifier,
    discoverViewModel: DiscoverViewModel = hiltViewModel(),
    onClickItem: (id: Int) -> Unit = {}
) {
    val discoverResults by discoverViewModel.discoverFlow.collectAsState()

    var displayDialog by rememberSaveable { mutableStateOf(true) }

    Box(modifier = Modifier.fillMaxSize()) {
        FloatingActionButton(
            onClick = {
                displayDialog = true
            },
            containerColor = MaterialTheme.colors.secondary,
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
        if (displayDialog) {
            Dialog(
                properties = DialogProperties(),
                onDismissRequest = { displayDialog = false }
            ) {
                DiscoverDialog(modifier)
            }
        }
        MoviesGridList(
            state = discoverResults,
            onClickRetry = { },
            onClickItem = onClickItem
        )
    }
}

@Composable
fun DiscoverDialog(modifier: Modifier = Modifier) {

    Surface(modifier.fillMaxWidth(), RoundedCornerShape(4.dp)) {
        Column {
            Text(
                text = stringResource(R.string.sort_by),
                style = androidx.compose.material3.MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(8.dp)
            )
            Text(
                text = stringResource(R.string.minimum_score),
                style = androidx.compose.material3.MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(8.dp)
            )
            Text(
                text = stringResource(R.string.select_genres),
                style = androidx.compose.material3.MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(8.dp)
            )
            TextButton(
                onClick = {},
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



