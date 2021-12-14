package io.radev.roman

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
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
                modifier = Modifier
                    .width(200.dp)
                    .height(50.dp)
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
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = when (title) {
                "Travel" -> Icons.Filled.TravelExplore
                "Day" -> Icons.Filled.Restaurant
                "Set reminder" -> Icons.Filled.AddAlert
                else -> Icons.Filled.TripOrigin
            },
            contentDescription = "",
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Text(
            text = title,
//            modifier = Modifier.padding(8.dp)
        )
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
    showSystemUi = true
)
@Composable
fun DefaultPreview() {
    RomanappTheme {
        RomanApp()
    }
}



