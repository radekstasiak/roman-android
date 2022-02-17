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
    EatOut(title = "Eat Out", icon = null),
    Favorites(title = "Favorites", icon = Icons.Filled.Favorite),
    Settings(title = "Settings", icon = Icons.Filled.Settings);


    companion object {
        fun fromRoute(route: String?): RomanScreen =
            when (route?.substringBefore("/")) {
                Dashboard.name -> Dashboard
                EatOut.name -> EatOut
                Favorites.name -> Favorites
                Settings.name -> Settings
                null -> Dashboard
                else -> throw IllegalArgumentException("Route $route is not recognized.")
            }
    }
}