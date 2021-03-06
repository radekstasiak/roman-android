package io.radev.roman.domain

import io.radev.roman.CoroutineDispatcher
import io.radev.roman.data.PlacesRepository
import io.radev.roman.domain.model.NetworkStatus
import io.radev.roman.network.NetworkResponse
import io.radev.roman.network.model.PlaceEntity
import kotlinx.coroutines.withContext

/*
 * Created by Radoslaw on 20/02/2022.
 * radev.io 2022.
 * Peace and Love.
 */

interface GetPlacesUseCase {

    suspend fun getPlaces(
        category: String,
        lat: String,
        lon: String
    ): GetPlacesDomainModel
}

class GetPlacesUseCaseImpl(
    private val placesRepository: PlacesRepository,
    private val dispatcher: CoroutineDispatcher
) : GetPlacesUseCase {
    override suspend fun getPlaces(
        category: String,
        lat: String,
        lon: String
    ): GetPlacesDomainModel {
        return withContext(dispatcher.IO) {
            val response = placesRepository.getPlaces(
                category = category,
                lat = lat,
                lon = lon
            )

            when (response) {
                is NetworkResponse.Success -> GetPlacesDomainModel(
                    networkStatus = NetworkStatus.Success,
                    results = response.body.results
                )
                is NetworkResponse.ApiError -> GetPlacesDomainModel(
                    networkStatus = NetworkStatus.ApiError(
                        message = response.error?.toString() ?: response.code.toString()
                    )
                )
                is NetworkResponse.NetworkError -> GetPlacesDomainModel(networkStatus = NetworkStatus.NetworkError)
                is NetworkResponse.UnknownError -> GetPlacesDomainModel(
                    networkStatus = NetworkStatus.UnknownError(
                        message = response.error?.toString() ?: ""
                    )
                )
            }
        }
    }

}

data class GetPlacesDomainModel(
    val networkStatus: NetworkStatus,
    val results: List<PlaceEntity> = listOf()
)