package com.example.movies2

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.movies2.navigation.MoviesDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Scaffold(bottomBar = {
        MoviesBottomNavigation(navController, modifier)
    }) {
        MovieNavHost(
            navController = navController,
            modifier = modifier.padding(it)
        )
    }
}

@Composable
fun MoviesBottomNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.background,
        modifier = modifier
    ) {
        var state by rememberSaveable { mutableStateOf(MoviesDestination.HOME) }

        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null
                )
            },
            label = {
                Text(stringResource(R.string.home))
            },
            selected = state == MoviesDestination.HOME,
            onClick = {
                state = MoviesDestination.HOME
                navController.navigateSingleTopTo(MoviesDestination.HOME)
            }
        )
        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            },
            label = {
                Text(stringResource(R.string.search))
            },
            selected = state == MoviesDestination.SEARCH,
            onClick = {
                state = MoviesDestination.SEARCH
                navController.navigateSingleTopTo(MoviesDestination.SEARCH)
            }
        )
    }
}

@Composable
fun MovieNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = MoviesDestination.HOME,
        modifier = modifier
    ) {
        composable(route = MoviesDestination.HOME) {
            HomeScreen(
                onClickSeeAllNowPlaying = {
                    navController.navigateSingleTopTo(MoviesDestination.NOW_SHOWING)
                },
                onClickSeeAllUpcoming = {
                    navController.navigateSingleTopTo(MoviesDestination.UPCOMING)
                },
                onClickSeeAllTopRated = {
                    navController.navigateSingleTopTo(MoviesDestination.TOP_RATED)
                },
                onClickSeeAllPopular = {
                    navController.navigateSingleTopTo(MoviesDestination.POPULAR)
                }
            )
        }
        composable(route = MoviesDestination.NOW_SHOWING) {
            SeeAllNowPlayingScreen()
        }
        composable(route = MoviesDestination.UPCOMING) {
            SeeAllUpcomingScreen()
        }
        composable(route = MoviesDestination.TOP_RATED) {
            SeeAllTopRatedScreen()
        }
        composable(route = MoviesDestination.POPULAR) {
            SeeAllPopularScreen()
        }
        composable(route = MoviesDestination.DETAIL) {
            MovieDetailsScreen()
        }
        composable(route = MoviesDestination.SEARCH) {
            SearchScreen()
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }