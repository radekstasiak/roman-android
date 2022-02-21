package io.radev.roman.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

/*
 * Created by radoslaw on 17/02/2022.
 * radev.io 2022.
 */

enum class RomanScreen(
    val title: String,
    val icon: ImageVector?
) {
    Dashboard(title = "Dashboard", icon = Icons.Filled.Menu),
    Favorites(title = "Favorites", icon = Icons.Filled.Favorite),
    Settings(title = "Settings", icon = Icons.Filled.Settings),
    Travel(title = "Travel", icon = null),
    Places(title = "Places", icon = null),
    SetReminder(title = "Set reminder", icon = null);


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