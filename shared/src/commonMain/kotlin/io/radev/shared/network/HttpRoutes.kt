package io.radev.shared.network


/*
 * Created by Radoslaw on 18/02/2022.
 * radev.io 2022.
 * Peace and Love.
 */

object HttpRoutes {
    private const val BASE_URL = "https://api.foursquare.com/v3"
    const val GET_PLACES = "$BASE_URL/places/search?"

    object HttpParams {
        const val PARAM_QUERY = "query="
        const val PARAM_LL = "ll="
    }
}
