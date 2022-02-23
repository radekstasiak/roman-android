package io.radev.roman.data

import com.google.android.gms.common.api.ApiException
import io.radev.roman.CoroutineDispatcher
import io.radev.roman.network.NetworkResponse
import io.radev.roman.network.PlacesService
import io.radev.roman.network.model.PlacesResponse
import io.radev.roman.network.toNetworkResponse
import kotlinx.coroutines.withContext

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
    ): NetworkResponse<PlacesResponse, ApiException> {
        return withContext(dispatcher.IO) {
            try {
                val response = placesService.getPlaces(
                    category = category,
                    lat = lat,
                    lon = lon
                )
                NetworkResponse.Success(body = response)
            } catch (exception: Exception) {
                exception.toNetworkResponse()
            }

        }
    }
}

interface PlacesRepository {
    suspend fun getPlaces(
        category: String,
        lat: String,
        lon: String
    ): NetworkResponse<PlacesResponse, ApiException>
}