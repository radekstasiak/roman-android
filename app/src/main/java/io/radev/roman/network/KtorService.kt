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
private const val TIME_OUT = 60_000

@Suppress("EXPERIMENTAL_API_USAGE_FUTURE_ERROR")
val client = HttpClient(Android) {
    install(Logging) {
        logger = Logger.DEFAULT
        level = LogLevel.HEADERS
    }
    install(ResponseObserver) {
        onResponse { response ->
            Log.d("HTTP status:", "${response.status.value}")
        }
    }

    install(DefaultRequest) {
        headers {
            append(HttpHeaders.Authorization, "apiKey")
            append(HttpHeaders.ContentType, ContentType.Application.Json)
        }
    }

    install(JsonFeature) {
        serializer = GsonSerializer()
    }
}

object HttpRoutes {
    private const val BASE_URL = "https://api.foursquare.com/v3"
    const val FETCH_IMAGES_URL = "${BASE_URL}/places/search?"

    object HttpParams {
        const val PARAM_QUERY = "query="
        const val PARAM_LL = "ll="
    }
}
