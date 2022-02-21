package io.radev.roman.ui.places

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.radev.roman.R
import io.radev.roman.ui.common.ViewState
import org.koin.androidx.compose.getViewModel

/*
 * Created by Radoslaw on 21/02/2022.
 * radev.io 2022.
 * Peace and Love.
 */

@Composable
fun PlacesScreen(
    placesViewModel: PlacesViewModel = getViewModel(),
    onPlaceSelected: (PlaceUiModel) -> Unit
) {
    val onRetryClicked = {
        placesViewModel.getPlaces()
    }
    when (val state = placesViewModel.uiState.collectAsState().value) {
        ViewState.Loading -> PlacesLoading()
        is ViewState.Loaded -> PlacesLoaded(
            places = state.uiModel,
            onPlaceSelected = onPlaceSelected
        )
        ViewState.NoNetwork -> NoNetwork(onRetryClicked = onRetryClicked)
        is ViewState.NetworkError -> NetworkError(state.message, onRetryClicked = onRetryClicked)
        is ViewState.Error -> ErrorScreen(message = state.message, onRetryClicked = onRetryClicked)
    }
}

@Composable
fun PlacesLoading() {
    Text(
        text = stringResource(id = R.string.api_in_progress),
        style = MaterialTheme.typography.body1
    )
}

@Composable
fun PlacesLoaded(
    modifier: Modifier = Modifier,
    places: List<PlaceUiModel>,
    onPlaceSelected: (PlaceUiModel) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(places) { place ->
            Card(
                backgroundColor = MaterialTheme.colors.primary,
                shape = MaterialTheme.shapes.small.copy(CornerSize(percent = 15)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable { onPlaceSelected(place) }
            ) {
                Column {
                    //TODO display icon
                    Text(text = place.name, style = MaterialTheme.typography.body1)
                    Text(text = place.address, style = MaterialTheme.typography.body2)
                }
            }
        }
    }
}

@Composable
fun NoNetwork(onRetryClicked: () -> Unit) =
    ErrorScreen(
        message = stringResource(id = R.string.api_no_network),
        onRetryClicked = onRetryClicked
    )

@Composable
fun NetworkError(message: String, onRetryClicked: () -> Unit) =
    ErrorScreen(
        message = String.format(stringResource(id = R.string.api_no_network), message),
        onRetryClicked = onRetryClicked
    )


@Composable
fun ErrorScreen(
    message: String,
    onRetryClicked: () -> Unit
) {
    Column {
        Text(
            text = String.format(stringResource(id = R.string.api_in_progress), message),
            style = MaterialTheme.typography.body1
        )
        Button(onClick = onRetryClicked) {
            Text(text = stringResource(id = R.string.api_try_again))
        }
    }

}