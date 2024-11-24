package com.example.movies2

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.moveis_ui.R
import com.example.moveis_ui.search.SearchViewModel

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
    onClickItem: (id: Int) -> Unit = {}
) {
    val gridState = rememberLazyGridState()
    val searchResults by viewModel.searchFlow.collectAsState()

    Column {

        Spacer(Modifier.height(16.dp))
        SearchBar(
            Modifier.padding(horizontal = 16.dp)
        ) { searchTerm: String -> viewModel.search(searchTerm) }
        Spacer(Modifier.height(16.dp))
        MoviesGridList(
            state = searchResults,
            lazyGridState = gridState,
            onClickRetry = {  },
            onClickItem = onClickItem,
            loadMore = { page -> viewModel.load(page) }
        )
    }

}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onSearch: (searchTerm: String) -> Unit
) {

    var searchTerm by rememberSaveable { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        value = searchTerm,
        onValueChange = { searchTerm = it },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        },
//        colors = TextFieldDefaults.textFieldColors(
//            backgroundColor = MaterialTheme.colorScheme.surface
//        ),
        placeholder = {
            Text(stringResource(R.string.search))
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions (
            onSearch = {
                onSearch(searchTerm)
                keyboardController?.hide()
           }
        ),
        singleLine = true,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
    )
}