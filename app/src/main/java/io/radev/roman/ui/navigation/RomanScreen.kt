package io.radev.roman.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import io.radev.roman.R

/*
 * Created by radoslaw on 17/02/2022.
 * radev.io 2022.
 */

enum class RomanScreen(
    val id: Int,
    val icon: ImageVector?
) {
    Dashboard(id = R.string.screen_dashboard_title, icon = Icons.Filled.Menu),
    Favorites(id = R.string.screen_favourites_title, icon = Icons.Filled.Favorite),
    Settings(id = R.string.screen_settings_title, icon = Icons.Filled.Settings),
    Travel(id = R.string.screen_travel_title, icon = Icons.Filled.TravelExplore),
    Places(id = R.string.screen_places_title, icon = Icons.Filled.Restaurant),
    SetReminder(id = R.string.screen_set_reminder_title, icon = Icons.Filled.AddAlert);


    companion object {
        fun fromRoute(route: String?): RomanScreen =
            when (route?.substringBefore("/")) {
                Dashboard.name -> Dashboard
                Favorites.name -> Favorites
                Settings.name -> Settings
                Travel.name -> Travel
                Places.name -> Places
                SetReminder.name -> SetReminder
                null -> Dashboard
                else -> throw IllegalArgumentException("Route $route is not recognized.")
            }
    }
}