package io.radev.roman.ui.navigation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.radev.roman.ui.dashboard.DashboardBodyContent

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
            DashboardBodyContent(navController)
        }
        composable(route = RomanScreen.Favorites.name) {
            Text(text = "Favorites")
        }
        composable(route = RomanScreen.Settings.name) {
            Text(text = "Settings")
        }
        composable(route = RomanScreen.Travel.name) {
            Text(text = "Travel")
        }
        composable(route = RomanScreen.EatOut.name) {
            Text(text = "EatOut")
        }
        composable(route = RomanScreen.SetReminder.name) {
            Text(text = "Set Reminder")
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