package io.radev.roman.data

import com.google.android.gms.common.api.ApiException
import io.radev.roman.CoroutineDispatcher
import io.radev.roman.network.NetworkResponse
import io.radev.roman.network.PlacesService
import io.radev.roman.network.model.PlacesResponse
import io.radev.roman.network.toNetworkResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/*
 * Created by Radoslaw on 19/02/2022.
 * radev.io 2022.
 * Peace and Love.
 */

class PlacesRepositoryImpl(
    private val placesService: PlacesService,
    private val dispatcher: CoroutineDispatcher
) : PlacesRepository {
    override suspend fun getPlaces(
        category: String,
        lat: String,
        lon: String
    ): Flow<NetworkResponse<PlacesResponse, ApiException>> {
        return flow<NetworkResponse<PlacesResponse, ApiException>> {
            val result = placesService.getPlaces(
                category = category,
                lat = lat,
                lon = lon
            )
            emit(NetworkResponse.Success(body = result))
        }.catch { exception -> emit(exception.toNetworkResponse()) }.flowOn(dispatcher.IO)
    }

}

interface PlacesRepository {
    suspend fun getPlaces(
        category: String,
        lat: String,
        lon: String
    ): Flow<NetworkResponse<PlacesResponse, ApiException>>
}