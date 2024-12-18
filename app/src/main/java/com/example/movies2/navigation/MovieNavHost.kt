package com.example.movies2

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MovieFilter
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.moveis_ui.navigation.MovieNavHost
import com.example.moveis_ui.navigation.MoviesDestination

@Composable
fun AppScreen(
    navController: NavHostController,
    windowSize: WindowSizeClass,
    modifier: Modifier = Modifier
) {

    if (windowSize.widthSizeClass == WindowWidthSizeClass.Compact) {
        Scaffold(
            bottomBar = {
                MoviesBottomNavigation(navController, modifier)
            }
        ) {
            MovieNavHost(
                navController = navController,
                modifier = modifier.padding(it),
                windowSize = windowSize
            )
        }
    } else {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            MoviesNavigationRail(navController = navController)
            MovieNavHost(
                navController = navController,
                windowSize = windowSize
            )
        }
    }

}

@Composable
fun MoviesBottomNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.background,
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
                    imageVector = Icons.Default.MovieFilter,
                    contentDescription = null
                )
            },
            label = {
                Text(stringResource(R.string.discover))
            },
            selected = state == MoviesDestination.DISCOVER,
            onClick = {
                state = MoviesDestination.DISCOVER
                navController.clearBackStack(MoviesDestination.DISCOVER)
                navController.navigateSingleTopTo(MoviesDestination.DISCOVER)
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
                navController.clearBackStack(MoviesDestination.SEARCH)
                navController.navigateSingleTopTo(MoviesDestination.SEARCH)
            }
        )
    }
}

@Composable
fun MoviesNavigationRail(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavigationRail(
        modifier = Modifier
            .fillMaxHeight()
    ) {
        var state by rememberSaveable { mutableStateOf(MoviesDestination.HOME) }
        NavigationRailItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null
                )
            },
            label = {
                Text(stringResource(R.string.home))
            },
            alwaysShowLabel = false,
            selected = state == MoviesDestination.HOME,
            onClick = {
                state = MoviesDestination.HOME
                navController.navigateSingleTopTo(MoviesDestination.HOME)
            }
        )
        NavigationRailItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.MovieFilter,
                    contentDescription = null
                )
            },
            label = {
                Text(stringResource(R.string.discover))
            },
            alwaysShowLabel = false,
            selected = state == MoviesDestination.DISCOVER,
            onClick = {
                state = MoviesDestination.DISCOVER
                navController.clearBackStack(MoviesDestination.DISCOVER)
                navController.navigateSingleTopTo(MoviesDestination.DISCOVER)
            }
        )
        NavigationRailItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            },
            label = {
                Text(stringResource(R.string.search))
            },
            alwaysShowLabel = false,
            selected = state == MoviesDestination.SEARCH,
            onClick = {
                state = MoviesDestination.SEARCH
                navController.clearBackStack(MoviesDestination.SEARCH)
                navController.navigateSingleTopTo(MoviesDestination.SEARCH)
            }
        )
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
        restoreState = false
    }