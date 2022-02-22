package io.radev.roman.ui.navigation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.radev.roman.ui.dashboard.DashboardBodyContent
import io.radev.roman.ui.places.PlacesScreen
import io.radev.roman.ui.places.PlacesViewModel
import org.koin.androidx.compose.getViewModel

/*
 * Created by Radoslaw on 17/02/2022.
 * radev.io 2022.
 * Peace and Love.
 */


@Composable
fun RomanNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = RomanScreen.Dashboard.name,
        modifier = modifier
    ) {
        composable(route = RomanScreen.Dashboard.name) {
            DashboardBodyContent(navigationController = navController)
        }
        composable(route = RomanScreen.Favorites.name) {
            Text(text = stringResource(id = RomanScreen.Favorites.id))
        }
        composable(route = RomanScreen.Settings.name) {
            Text(text = stringResource(id = RomanScreen.Settings.id))
        }
        composable(route = RomanScreen.Travel.name) {
            Text(text = stringResource(id = RomanScreen.Travel.id))
        }
        composable(route = RomanScreen.Places.name) {
            val placesViewModel = getViewModel<PlacesViewModel>()
            placesViewModel.getPlaces()
            PlacesScreen(placesViewModel = placesViewModel,
                onPlaceSelected = { })
        }
        composable(route = RomanScreen.SetReminder.name) {
            Text(text = stringResource(id = RomanScreen.SetReminder.id))
        }
    }
}

fun navigateTo(
    navController: NavHostController,
    screenRoute: String,
    popUpToScreenRoute: String = "",
    navOptionsBuilder: (NavOptionsBuilder) -> Unit
) {
    navController.navigate(screenRoute) {
        if (popUpToScreenRoute.isNotBlank()) {
            popUpTo(popUpToScreenRoute) {
                saveState = true
            }
        }
        navOptionsBuilder(this)
    }
}