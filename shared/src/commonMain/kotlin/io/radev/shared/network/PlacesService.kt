package io.radev.shared.network

import io.ktor.client.request.*
import io.ktor.util.*
import io.radev.shared.network.HttpRoutes.GET_PLACES
import io.radev.shared.network.model.PlacesResponse

/*
 * Created by Radoslaw on 19/02/2022.
 * radev.io 2022.
 * Peace and Love.
 */

@InternalAPI
class PlacesServiceImpl(
    private val apiClient: ApiClient
) : PlacesService {
    override suspend fun getPlaces(
        category: String,
        lat: String,
        lon: String
    ): PlacesResponse {
        return apiClient.httpClient.get {
            url(
                GET_PLACES +
                        HttpRoutes.HttpParams.PARAM_QUERY + category + "&" +
                        HttpRoutes.HttpParams.PARAM_LL + "$lat,$lon"
            )
        }
    }

}


interface PlacesService {

    // curl --request GET \
    // --url 'https://api.foursquare.com/v3/places/search?query=restaurants&ll=53.801277%2C-1.548567' \
    // --header 'Accept: application/json' \
    // --header 'Authorization: apiKey'
    suspend fun getPlaces(
        category: String,
        lat: String,
        lon: String
    ): PlacesResponse
}
