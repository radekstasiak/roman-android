package io.radev.roman.ui.places

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.radev.roman.R
import io.radev.roman.ui.common.ErrorScreen
import io.radev.roman.ui.common.NetworkErrorScreen
import io.radev.roman.ui.common.NoNetworkScreen
import io.radev.roman.ui.common.ViewState
import io.radev.roman.ui.theme.RomanappTheme
import org.koin.androidx.compose.getViewModel

/*
 * Created by Radoslaw on 21/02/2022.
 * radev.io 2022.
 * Peace and Love.
 */

@Composable
fun PlacesScreen(
    modifier: Modifier = Modifier,
    placesViewModel: PlacesViewModel = getViewModel(),
    onPlaceSelected: (PlaceUiModel) -> Unit
) {
    val uiState by placesViewModel.uiState.collectAsState()
    val onRetryClicked = {
        placesViewModel.getPlaces()
    }

    when (uiState) {
        ViewState.Loading -> PlacesLoading()
        is ViewState.Loaded -> PlacesLoaded(
            modifier = modifier,
            places = (uiState as ViewState.Loaded<List<PlaceUiModel>>).uiModel,
            onPlaceSelected = onPlaceSelected
        )
        ViewState.NoNetwork -> NoNetworkScreen(onRetryClicked = onRetryClicked)
        is ViewState.ApiError -> NetworkErrorScreen(
            modifier = modifier,
            message = (uiState as ViewState.ApiError).message,
            onRetryClicked = onRetryClicked
        )
        is ViewState.Error -> ErrorScreen(
            modifier = modifier,
            message = (uiState as ViewState.Error).message,
            onRetryClicked = onRetryClicked
        )
    }
}

@Composable
fun PlacesLoading(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .fillMaxHeight()
            .wrapContentHeight(align = Alignment.CenterVertically)
            .wrapContentWidth(align = Alignment.CenterHorizontally)
    ) {
        Text(
            text = stringResource(id = R.string.api_in_progress),
            style = MaterialTheme.typography.body1
        )
    }

}

@Composable
fun PlacesLoaded(
    modifier: Modifier = Modifier,
    places: List<PlaceUiModel>,
    onPlaceSelected: (PlaceUiModel) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .clipToBounds()
    ) {
        items(places) { place ->
            Card(
                shape = MaterialTheme.shapes.small.copy(CornerSize(percent = 5)),
                modifier = Modifier
                    .padding(8.dp)
                    .defaultMinSize(minHeight = 80.dp)
                    .fillMaxWidth()
                    .clickable { onPlaceSelected(place) }
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    //TODO display icon
                    Text(text = place.name, style = MaterialTheme.typography.body1)
                    Text(
                        modifier = Modifier.padding(vertical = 4.dp),
                        text = place.address,
                        style = MaterialTheme.typography.body2
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PlacesLoadedPreview() {
    val uiModelList = listOf(
        PlaceUiModel(
            name = "Place 1",
            address = "Street, Post Code, City",
            lat = 0.0,
            lon = 0.0,
            icon = ""
        ),
        PlaceUiModel(
            name = "Place 2",
            address = "Street, Post Code, City",
            lat = 0.0,
            lon = 0.0,
            icon = ""
        ),
        PlaceUiModel(
            name = "Place 3",
            address = "Street, Post Code, City",
            lat = 0.0,
            lon = 0.0,
            icon = ""
        )
    )
    RomanappTheme {
        PlacesLoaded(places = uiModelList) {}
    }
}


@Preview(showBackground = true)
@Composable
fun PlacesLoadingPreview() {
    RomanappTheme {
        PlacesLoading()
    }
}