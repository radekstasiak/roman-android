package io.radev.roman

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import io.radev.roman.ui.navigation.RomanNavHost
import io.radev.roman.ui.navigation.RomanScreen
import io.radev.roman.ui.navigation.navigateTo
import io.radev.roman.ui.theme.RomanappTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DashboardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RomanappTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    RomanApp()
                }
            }
        }
    }
}


@Composable
fun RomanApp() {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val backstackEntry = navController.currentBackStackEntryAsState()
    val currentScreen = RomanScreen.fromRoute(
        backstackEntry.value?.destination?.route
    )
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            RomanTopBar(
                scope = scope,
                scaffoldState = scaffoldState,
                title = currentScreen.title
            )
        },
        drawerContent = {
            RomanMenuDrawer(
                navController = navController,
                scope = scope,
                scaffoldState = scaffoldState
            )
        },
        bottomBar = {
            RomanBottomNavigation(
                navController = navController,
                currentScreen = currentScreen
            )
        }) { innerPaddings ->
        RomanNavHost(navController = navController, modifier = Modifier.padding(innerPaddings))
    }

}


@Composable
fun RomanMenuDrawer(
    navController: NavHostController,
    scope: CoroutineScope, scaffoldState: ScaffoldState
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxHeight()
            .padding(
                start = 16.dp,
                top = 16.dp,
                bottom = 16.dp
            )
    ) {
        val (column, bottomHint) = createRefs()

        Column(
            modifier = Modifier
                .constrainAs(column) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
        ) {
            for (i in 0 until 5) {
                TextButton(
                    modifier = Modifier
                        .padding(top = 16.dp),
                    onClick = {
                        scope.launch { scaffoldState.drawerState.close() }
                        navigateTo(
                            navController = navController,
                            screenRoute = RomanScreen.SetReminder.name,
                            popUpToScreenRoute = RomanScreen.Dashboard.name
                        ) {}
                    }
                ) {
                    Text(text = "Item $i")
                }
            }
            Button(
                modifier = Modifier
                    .padding(top = 16.dp),
                onClick = {
                    scope.launch { scaffoldState.drawerState.close() }
                },
                content = { Text("Close Drawer") }
            )


        }
        Text(
            modifier = Modifier
                .constrainAs(bottomHint) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)

                },
            text = "<<< Swipe to close <<<",
            color = LocalContentColor.current.copy(alpha = 0.3f)
        )
    }

}


@Composable
fun RomanTopBar(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    title: String
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = { scope.launch { scaffoldState.drawerState.open() } }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "",
                )
            }

        }
    )

}

@Composable
fun RomanBottomNavigation(
    navController: NavHostController,
    currentScreen: RomanScreen
) {
    val navigationItems = listOf(
        RomanScreen.Dashboard,
        RomanScreen.Favorites,
        RomanScreen.Settings
    )
    BottomNavigation {
        navigationItems.forEachIndexed { _, navigationItem ->
            BottomNavigationItem(
                icon = {
                    navigationItem.icon?.let { icon ->
                        Icon(imageVector = icon, contentDescription = "")
                    }
                },
                selected = navigationItem == currentScreen,
                label = { Text(text = navigationItem.title) },
                onClick = {
                    navigateTo(
                        navController = navController,
                        screenRoute = navigationItem.name,
                        popUpToScreenRoute = RomanScreen.Dashboard.name
                    ) {
                        it.launchSingleTop = true
                    }
                }
            )
        }
    }


}


@Preview(
    showBackground = true,
    device = Devices.PIXEL_3A
)
@Composable
fun MenuDrawerPreview() {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    scaffoldState.drawerState.isOpen
    RomanappTheme {
        RomanMenuDrawer(
            navController = navController,
            scope = scope,
            scaffoldState = scaffoldState
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RomanTopBarPreview() {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    RomanappTheme {
        RomanTopBar(scope = scope, scaffoldState = scaffoldState, title = "Preview")
    }
}

@Preview(showBackground = true)
@Composable
fun RomanBottomBarPreview() {
    RomanappTheme {
        val navController = rememberNavController()
        val currentScreen = RomanScreen.Dashboard
        RomanBottomNavigation(navController, currentScreen)
    }
}


@Preview(
    showBackground = true,
    showSystemUi = false
)
@Composable
fun DefaultPreview() {
    RomanappTheme {
        RomanApp()
    }
}





