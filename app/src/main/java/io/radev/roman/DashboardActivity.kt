package io.radev.roman

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Help
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import io.radev.roman.ui.theme.RomanappTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { RomanTopBar(scope = scope, scaffoldState = scaffoldState) },
        drawerContent = { RomanMenuDrawer(scope = scope, scaffoldState = scaffoldState) },
        bottomBar = { RomanBottomNavigation() }) { innerPaddings ->
        DashboardBodyContent(modifier = Modifier.padding(innerPaddings))
    }

}

@Composable
fun RomanMenuDrawer(scope: CoroutineScope, scaffoldState: ScaffoldState) {
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
                    onClick = {}
                ) {
                    Text(text = "Item $i")
                }
            }
            Button(
                modifier = Modifier
                    .padding(top = 16.dp),
                onClick = { scope.launch { scaffoldState.drawerState.close() } },
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
fun RomanTopBar(scope: CoroutineScope, scaffoldState: ScaffoldState) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.app_name)) },
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
fun RomanBottomNavigation() {
    var selected by rememberSaveable {
        mutableStateOf("")
    }
    val navigationItems = listOf<NavigationItem>(
        NavigationItem(title = "Dashboard", icon = Icons.Filled.Menu),
        NavigationItem(title = "Bookmarks", icon = Icons.Filled.Bookmark),
        NavigationItem(title = "Settings", icon = Icons.Filled.Settings)
    )
    BottomNavigation() {
        navigationItems.forEachIndexed { _, navigationItem ->
            BottomNavigationItem(
                icon = { Icon(imageVector = navigationItem.icon, contentDescription = "") },
                selected = navigationItem.title == selected,
                label = { Text(text = navigationItem.title) },
                onClick = { selected = navigationItem.title }
            )
        }
    }


}

@Composable
fun DashboardBodyContent(modifier: Modifier = Modifier) {
    Surface(color = MaterialTheme.colors.background) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(vertical = 8.dp, horizontal = 8.dp)
        ) {
            DashboardMenuItems()
        }

    }
}


@Composable
fun DashboardMenuItems(modifier: Modifier = Modifier) {
    val itemList = arrayListOf("Travel", "Day", "Set reminder")
    LazyColumn(modifier = modifier) {
        items(itemList) { item ->
            Card(
                backgroundColor = MaterialTheme.colors.primary,
                shape = MaterialTheme.shapes.small.copy(CornerSize(percent = 15)),
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 100.dp)
//                    .height(100.dp)
                    .padding(vertical = 4.dp)
                    .clickable {
                        when (item) {
                            "Set reminder" -> {
                                Log.d("clickable_debug", "will navigate to the reminder screen")
                            }
                        }
                    }
            ) {
                CardContent(title = item)
            }
        }
    }

}

@Composable
fun CardContent(title: String) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(top = 16.dp)

        ) {
            Icon(
                imageVector = when (title) {
                    "Travel" -> Icons.Filled.TravelExplore
                    "Day" -> Icons.Filled.Restaurant
                    "Set reminder" -> Icons.Filled.AddAlert
                    else -> Icons.Filled.Favorite
                },
                contentDescription = "",
                modifier = Modifier
                    .padding(start = 16.dp, end = 8.dp)
            )
            Text(
                modifier = Modifier
                    .weight(1f),
                text = title,
                style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Bold)

            )
            IconButton(
                onClick = { expanded = !expanded },
                modifier = Modifier
                    .padding(end = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Help,
                    contentDescription = "",

                    )
            }
        }

        if (expanded) Text(
            text = when (title) {
                "Travel" -> "Plan a trip together"
                "Day" -> "Plan a night out together"
                "Set reminder" -> "Set a reminder to stay romantic"
                else -> ""
            },
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.body2.copy(fontSize = 22.sp)
        )
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
    scaffoldState.drawerState.isOpen
    RomanappTheme {
        RomanMenuDrawer(scope = scope, scaffoldState = scaffoldState)
    }
}

@Preview(showBackground = true)
@Composable
fun RomanTopBarPreview() {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    RomanappTheme {
        RomanTopBar(scope = scope, scaffoldState = scaffoldState)
    }
}

@Preview(showBackground = true)
@Composable
fun RomanBottomBarPreview() {
    RomanappTheme {
        RomanBottomNavigation()
    }
}

@Preview(
    showBackground = true,
    name = "Dashboard Item",
    group = "Item"
)
@Composable
fun DashboardItemPreview() {
    RomanappTheme {
        CardContent(title = "Test title")
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

data class NavigationItem(
    val title: String,
    val icon: ImageVector
)




