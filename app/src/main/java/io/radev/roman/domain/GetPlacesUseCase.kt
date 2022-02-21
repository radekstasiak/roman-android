package io.radev.roman.domain

import io.radev.roman.CoroutineDispatcher
import io.radev.roman.data.PlacesRepository
import io.radev.roman.domain.model.NetworkStatus
import io.radev.roman.network.NetworkResponse
import io.radev.roman.network.model.PlaceEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart

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
    ): Flow<GetPlacesDomainModel>
}

class GetPlacesUseCaseImpl(
    private val placesRepository: PlacesRepository,
    private val dispatcher: CoroutineDispatcher
) : GetPlacesUseCase {
    override suspend fun getPlaces(
        category: String,
        lat: String,
        lon: String
    ): Flow<GetPlacesDomainModel> {
        return flow<GetPlacesDomainModel> {
            val result = placesRepository.getPlaces(
                category = category,
                lat = lat,
                lon = lon
            )
            emit(
                when (result) {
                    is NetworkResponse.Success -> GetPlacesDomainModel(
                        networkStatus = NetworkStatus.Success,
                        results = result.body.results
                    )
                    is NetworkResponse.ApiError -> GetPlacesDomainModel(
                        networkStatus = NetworkStatus.ApiError(
                            message = result.error?.toString() ?: result.code.toString()
                        )
                    )
                    is NetworkResponse.NetworkError -> GetPlacesDomainModel(networkStatus = NetworkStatus.NetworkError)
                    is NetworkResponse.UnknownError -> GetPlacesDomainModel(
                        networkStatus = NetworkStatus.UnknownError(
                            message = result.error?.toString() ?: ""
                        )
                    )
                }
            )
        }.onStart { emit(GetPlacesDomainModel(networkStatus = NetworkStatus.InProgress)) }
            .flowOn(dispatcher.IO)

    }

}

data class GetPlacesDomainModel(
    val networkStatus: NetworkStatus,
    val results: List<PlaceEntity> = listOf()
)