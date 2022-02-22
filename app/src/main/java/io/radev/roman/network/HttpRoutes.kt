package io.radev.roman.network

import android.util.Log
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.observer.*
import io.ktor.client.request.*
import io.ktor.http.*

/*
 * Created by Radoslaw on 18/02/2022.
 * radev.io 2022.
 * Peace and Love.
 */

object HttpRoutes {
    private const val BASE_URL = "https://api.foursquare.com/v3"
    const val GET_PLACES = "${BASE_URL}/places/search?"

    object HttpParams {
        const val PARAM_QUERY = "query="
        const val PARAM_LL = "ll="
    }
}
