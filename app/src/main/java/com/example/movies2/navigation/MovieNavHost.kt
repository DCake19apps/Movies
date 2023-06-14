package com.example.movies2

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.movies2.navigation.MoviesDestination

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