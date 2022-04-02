package io.radev.shared.data

import io.radev.shared.network.toNetworkResponse

/*
 * Created by Radoslaw on 19/02/2022.
 * radev.io 2022.
 * Peace and Love.
 */

class PlacesRepositoryImpl(
    private val placesService: io.radev.shared.network.PlacesService,
//    private val dispatcher: CoroutineDispatcher
) : PlacesRepository {
    override suspend fun getPlaces(
        category: String,
        lat: String,
        lon: String
    ): io.radev.shared.network.NetworkResponse<io.radev.shared.network.model.PlacesResponse, Exception> {
//        return withContext(dispatcher.IO) {
        return try {
            val response = placesService.getPlaces(
                category = category,
                lat = lat,
                lon = lon
            )
            io.radev.shared.network.NetworkResponse.Success(body = response)
        } catch (exception: Exception) {
            exception.toNetworkResponse()
        }


    }
}

interface PlacesRepository {
    suspend fun getPlaces(
        category: String,
        lat: String,
        lon: String
    ): io.radev.shared.network.NetworkResponse<io.radev.shared.network.model.PlacesResponse, Exception>
}