package io.radev.roman.di

import android.content.Context
import android.util.Log
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.observer.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.radev.roman.R
import io.radev.roman.network.PlacesService
import io.radev.roman.network.PlacesServiceImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.bind
import org.koin.dsl.module

/*
 * Created by Radoslaw on 20/02/2022.
 * radev.io 2022.
 * Peace and Love.
 */

val networkModule = module {
    single { provideHttpClient(androidContext()) }
    single { PlacesServiceImpl(get()) } bind PlacesService::class
}

@Suppress("EXPERIMENTAL_API_USAGE_FUTURE_ERROR")
fun provideHttpClient(context: Context): HttpClient =
    HttpClient(Android) {
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
                append(HttpHeaders.ContentType, ContentType.Application.Json)
                append(HttpHeaders.Authorization, context.getString(R.string.FOURSQUARE_API_KEY))
            }
        }

        install(JsonFeature) {
            serializer = GsonSerializer()
        }

//        HttpResponseValidator {
//            validateResponse { response: HttpResponse ->
//                val statusCode = response.status.value
//response.headers.entries()
//                println("HTTP status: $statusCode")
//
//                when (statusCode) {
//                    in 300..399 -> throw RedirectResponseException(response)
//                    in 400..499 -> throw ClientRequestException(response)
//                    in 500..599 -> throw ServerResponseException(response)
//                }
//
//                if (statusCode >= 600) {
//                    throw ResponseException(response)
//                }
//            }
//
//            handleResponseException { cause: Throwable ->
//                throw cause
//            }
//        }
    }