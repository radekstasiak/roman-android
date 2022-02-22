package io.radev.roman.network.model

import com.google.gson.annotations.SerializedName

/*
 * Created by Radoslaw on 19/02/2022.
 * radev.io 2022.
 * Peace and Love.
 */

class PlacesResponse {
    @SerializedName("results")
    val results: List<PlaceEntity> = listOf()
}