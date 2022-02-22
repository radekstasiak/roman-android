package io.radev.roman.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.radev.roman.R
import io.radev.roman.ui.theme.RomanappTheme

/*
 * Created by Radoslaw on 21/02/2022.
 * radev.io 2022.
 * Peace and Love.
 */

@Composable
fun NoNetworkScreen(
    modifier: Modifier = Modifier,
    onRetryClicked: () -> Unit
) =
    ErrorScreen(
        message = stringResource(id = R.string.api_no_network),
        onRetryClicked = onRetryClicked
    )

@Composable
fun NetworkErrorScreen(
    modifier: Modifier = Modifier,
    message: String,
    onRetryClicked: () -> Unit
) =
    ErrorScreen(
        message = String.format(stringResource(id = R.string.api_network_error), message),
        onRetryClicked = onRetryClicked
    )


@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    message: String,
    onRetryClicked: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .fillMaxHeight()
            .wrapContentHeight(align = Alignment.CenterVertically)
            .wrapContentWidth(align = Alignment.CenterHorizontally)
    ) {
        Text(
            text = String.format(stringResource(id = R.string.api_general_error), message),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body1
        )
        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 16.dp),
            onClick = onRetryClicked
        ) {
            Text(text = stringResource(id = R.string.api_try_again))
        }
    }

}

@Preview(showBackground = true)
@Composable
fun NoNetworkScreenPreview() {
    RomanappTheme {
        NoNetworkScreen() {}
    }
}

@Preview(showBackground = true)
@Composable
fun NetworkErrorScreenPreview() =
    RomanappTheme {
        NetworkErrorScreen(
            message = "Error message"
        ) {}
    }

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() =
    RomanappTheme {
        ErrorScreen(
            message = "Generic message"
        ) {}
    }