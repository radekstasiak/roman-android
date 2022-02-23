package io.radev.roman.ui.common

import io.radev.roman.domain.model.NetworkStatus

/*
 * Created by Radoslaw on 21/02/2022.
 * radev.io 2022.
 * Peace and Love.
 */

interface IViewState

sealed class ViewState<out T : Any> : IViewState {
    object Empty : ViewState<Nothing>()
    object Loading : ViewState<Nothing>()
    data class Loaded<T : Any>(val uiModel: T) : ViewState<T>()
    object NoNetwork : ViewState<Nothing>()
    data class ApiError(val message: String) : ViewState<Nothing>()
    data class Error(val message: String) : ViewState<Nothing>()
}

