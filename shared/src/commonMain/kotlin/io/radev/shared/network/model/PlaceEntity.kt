package io.radev.shared.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/*
 * Created by Radoslaw on 20/02/2022.
 * radev.io 2022.
 * Peace and Love.
 */

@Serializable
data class PlaceEntity(
    @SerialName("fsq_id") var fsqId: String? = null,
    @SerialName("categories") var categories: ArrayList<Categories> = arrayListOf(),
    @SerialName("chains") var chains: ArrayList<String> = arrayListOf(),
    @SerialName("distance") var distance: Int? = null,
    @SerialName("geocodes") var geocodes: Geocodes? = Geocodes(),
    @SerialName("location") var location: Location? = Location(),
    @SerialName("name") var name: String? = null,
    @SerialName("related_places") var relatedPlaces: RelatedPlaces? = RelatedPlaces(),
    @SerialName("timezone") var timezone: String? = null
) {
    @Serializable
    data class Categories(
        @SerialName("id") var id: Int? = null,
        @SerialName("name") var name: String? = null,
        @SerialName("icon") var icon: Icon? = Icon()
    )

    @Serializable
    data class Icon(
        @SerialName("prefix") var prefix: String? = null,
        @SerialName("suffix") var suffix: String? = null
    )

    @Serializable
    data class Geocodes(
        @SerialName("main") var main: Main? = Main()
    ) {

        @Serializable
        data class Main(
            @SerialName("latitude") var latitude: Double? = null,
            @SerialName("longitude") var longitude: Double? = null
        )
    }

    @Serializable
    data class Location(
        @SerialName("address") var address: String? = null,
        @SerialName("admin_region") var adminRegion: String? = null,
        @SerialName("country") var country: String? = null,
        @SerialName("cross_street") var crossStreet: String? = null,
        @SerialName("formatted_address") var formattedAddress: String? = null,
        @SerialName("locality") var locality: String? = null,
        @SerialName("neighborhood") var neighborhood: ArrayList<String> = arrayListOf(),
        @SerialName("post_town") var postTown: String? = null,
        @SerialName("postcode") var postcode: String? = null,
        @SerialName("region") var region: String? = null
    )

    @Serializable
    data class RelatedPlaces(
        @SerialName("children") var children: ArrayList<Children> = arrayListOf()
    ) {

        @Serializable
        data class Children(
            @SerialName("fsq_id") var fsqId: String? = null,
            @SerialName("name") var name: String? = null
        )
    }

}