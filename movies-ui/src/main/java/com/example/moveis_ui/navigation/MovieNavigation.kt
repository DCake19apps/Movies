package com.example.moveis_ui.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.moveis_ui.DiscoverScreen
import com.example.moveis_ui.HomeScreen
import com.example.moveis_ui.MovieDetailsScreen
import com.example.movies2.SearchScreen
import com.example.movies2.SeeAllNowPlayingScreen
import com.example.movies2.SeeAllPopularScreen
import com.example.movies2.SeeAllTopRatedScreen
import com.example.movies2.SeeAllUpcomingScreen


@Composable
fun MovieNavHost(
    navController: NavHostController,
    windowSize: WindowSizeClass,
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
                },
                onClickItem = {
                    navController.clearBackStack(MoviesDestination.DETAIL)
                    navController.navigateSingleTopTo(getDetailDestination(it))
                }
            )
        }
        composable(route = MoviesDestination.NOW_SHOWING) {
            SeeAllNowPlayingScreen(
                onClickItem = { navController.navigate(getDetailDestination(it)) }
            )
        }
        composable(route = MoviesDestination.UPCOMING) {
            SeeAllUpcomingScreen(
                onClickItem = { navController.navigate(getDetailDestination(it)) }
            )
        }
        composable(route = MoviesDestination.TOP_RATED) {
            SeeAllTopRatedScreen(
                onClickItem = { navController.navigate(getDetailDestination(it)) }
            )
        }
        composable(route = MoviesDestination.POPULAR) {
            SeeAllPopularScreen(
                onClickItem = { navController.navigate(getDetailDestination(it)) }
            )
        }
        composable(route = MoviesDestination.DETAIL) {
            MovieDetailsScreen(windowSize)
        }
        composable(route = MoviesDestination.DISCOVER) {
            DiscoverScreen(
                onClickItem = { navController.navigate(getDetailDestination(it)) }
            )
        }
        composable(route = MoviesDestination.SEARCH) {
            SearchScreen(
                onClickItem = { navController.navigate(getDetailDestination(it)) }
            )
        }
    }

}

fun getDetailDestination(id: Int): String {
    return MoviesDestination.DETAIL.replace("{movieId}", id.toString())
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