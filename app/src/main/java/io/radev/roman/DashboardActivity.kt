package io.radev.roman

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Help
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.radev.roman.ui.theme.RomanappTheme

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
    Surface(color = MaterialTheme.colors.background) {
        Column(
//            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(vertical = 8.dp, horizontal = 8.dp)
        ) {
            DashboardMenuItems()
        }

    }
}

@Composable
fun DashboardMenuItems() {
    val itemList = arrayListOf("Travel", "Day", "Set reminder")
    LazyColumn() {
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
                    else -> Icons.Filled.TripOrigin
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
//            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.body2.copy(fontSize = 22.sp)
        )
        //todo check how to add spring animation for the layout click
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



