package io.radev.shared.domain.model

/*
 * Created by Radoslaw on 21/02/2022.
 * radev.io 2022.
 * Peace and Love.
 */

sealed class NetworkStatus {
    object Success : NetworkStatus()
    object NetworkError : NetworkStatus()
    data class ApiError(val message: String) : NetworkStatus()
    data class UnknownError(val message: String) : NetworkStatus()
}