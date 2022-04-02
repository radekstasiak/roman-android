package io.radev.shared.network

import io.ktor.client.features.*

/*
 * Created by Radoslaw on 19/02/2022.
 * radev.io 2022.
 * Peace and Love.
 */

sealed class NetworkResponse<out T : Any, out U : Any> {
    /**
     * Success response with body
     */
    data class Success<T : Any>(val body: T) : NetworkResponse<T, Nothing>()

    /**
     * Failure response with body
     */
    data class ApiError(val error: Throwable?, val code: Int) : NetworkResponse<Nothing, Nothing>()

    /**
     * Network error
     */
    data class NetworkError(val error: Exception) : NetworkResponse<Nothing, Nothing>()

    /**
     * For example, json parsing error
     */
    data class UnknownError(val error: Throwable?) : NetworkResponse<Nothing, Nothing>()
}

//TODO expect/actual platform implementation to support java exceptions on Android
fun Throwable.toNetworkResponse() = when (this) {
    is ServerResponseException -> NetworkResponse.UnknownError(error = this)
    is ClientRequestException -> NetworkResponse.ApiError(
        error = this,
        code = this.response.status.value
    )
    is RedirectResponseException -> NetworkResponse.UnknownError(error = this)
//    is IOException -> NetworkResponse.NetworkError(error = this)
//    is UnknownHostException -> NetworkResponse.NetworkError(error = this)
    else -> NetworkResponse.UnknownError(error = this)
}