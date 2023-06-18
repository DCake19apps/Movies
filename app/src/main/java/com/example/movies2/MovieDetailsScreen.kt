package com.example.movies2

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.moveis_ui.details.CreditsViewModel
import com.example.moveis_ui.details.DetailsViewModel

@Composable
fun MovieDetailsScreen(
    detailsViewModel: DetailsViewModel = hiltViewModel(),
    creditsViewModel: CreditsViewModel = hiltViewModel()
) {

}