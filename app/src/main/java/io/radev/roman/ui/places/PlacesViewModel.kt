package io.radev.roman.ui.places

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.radev.roman.CoroutineDispatcher
import io.radev.roman.domain.GetPlacesUseCase
import io.radev.roman.domain.model.NetworkStatus
import io.radev.roman.network.model.PlaceEntity
import io.radev.roman.ui.common.ViewState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/*
 * Created by Radoslaw on 20/02/2022.
 * radev.io 2022.
 * Peace and Love.
 */

class PlacesViewModel(
    private val getPlacesUseCase: GetPlacesUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow<ViewState<List<PlaceUiModel>>>(ViewState.Empty)
    val uiState: StateFlow<ViewState<List<PlaceUiModel>>> = _uiState

    fun getPlaces() {
        viewModelScope.launch(dispatcher.IO) {
            getPlacesUseCase.getPlaces("restaurants", "53.801277", "-1.548567")
                .map { domainResponse ->
                    when (domainResponse.networkStatus) {
                        NetworkStatus.InProgress -> ViewState.Loading
                        NetworkStatus.Success -> ViewState.Loaded(domainResponse.results
                            .filter { it.geocodes?.main != null }
                            .map { it.toPlaceUiModel() })
                        is NetworkStatus.ApiError -> ViewState.NetworkError(message = domainResponse.networkStatus.message)
                        NetworkStatus.NetworkError -> ViewState.NoNetwork
                        is NetworkStatus.UnknownError -> ViewState.Error(domainResponse.networkStatus.message)
                    }
                }
                .flowOn(dispatcher.IO)
                .collect { _uiState.value = it }
        }
    }
}


data class PlaceUiModel(
    val name: String,
    val address: String,
    val icon: String,
    val lat: Double,
    val lon: Double
)


fun PlaceEntity.toPlaceUiModel(): PlaceUiModel = PlaceUiModel(
    name = this.name ?: "",
    address = this.location?.formattedAddress ?: "",
    icon = if (this.categories.isNotEmpty()) "${this.categories[0].icon?.prefix} + ${this.categories[0].icon?.suffix}" else "",
    lon = this.geocodes?.main?.longitude ?: 0.0,
    lat = this.geocodes?.main?.latitude ?: 0.0,
)