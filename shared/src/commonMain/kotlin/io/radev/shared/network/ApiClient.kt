package io.radev.shared.network

import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.observer.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.util.*

/*
 * Created by Radoslaw on 20/02/2022.
 * radev.io 2022.
 * Peace and Love.
 */

class ApiClient(
    private val apiKey: String
) {
    //TODO inject engine depending on the platform implementation
    @InternalAPI
    val httpClient = HttpClient() {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.HEADERS
        }
        install(ResponseObserver) {
            onResponse { response ->
//                Log.d("HTTP status:", "${response.status.value}")
            }
        }

        install(DefaultRequest) {
            headers {
                append(HttpHeaders.ContentType, ContentType.Application.Json)
                append(HttpHeaders.Authorization, apiKey)
            }
        }

        install(JsonFeature) {
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }
}