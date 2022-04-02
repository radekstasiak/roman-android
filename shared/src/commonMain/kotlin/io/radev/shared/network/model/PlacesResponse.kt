package io.radev.shared.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/*
 * Created by Radoslaw on 19/02/2022.
 * radev.io 2022.
 * Peace and Love.
 */

@Serializable
data class PlacesResponse(
    @SerialName("results")
    val results: List<PlaceEntity> = listOf()
)