package io.radev.roman.network

import io.ktor.client.features.*
import java.io.IOException

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
    data class NetworkError(val error: IOException) : NetworkResponse<Nothing, Nothing>()

    /**
     * For example, json parsing error
     */
    data class UnknownError(val error: Throwable?) : NetworkResponse<Nothing, Nothing>()
}

fun Exception.toNetworkResponse() = when (this) {
    is ServerResponseException -> NetworkResponse.UnknownError(error = this)
    is ClientRequestException -> NetworkResponse.ApiError(
        error = this,
        code = this.response.status.value
    )
    is RedirectResponseException -> NetworkResponse.UnknownError(error = this)
    is IOException -> NetworkResponse.NetworkError(error = this)
    else -> NetworkResponse.UnknownError(error = this)
}