package io.radev.roman.network.model

import com.google.gson.annotations.SerializedName

/*
 * Created by Radoslaw on 20/02/2022.
 * radev.io 2022.
 * Peace and Love.
 */

data class PlaceEntity(
    @SerializedName("fsq_id") var fsqId: String? = null,
    @SerializedName("categories") var categories: ArrayList<Categories> = arrayListOf(),
    @SerializedName("chains") var chains: ArrayList<String> = arrayListOf(),
    @SerializedName("distance") var distance: Int? = null,
    @SerializedName("geocodes") var geocodes: Geocodes? = Geocodes(),
    @SerializedName("location") var location: Location? = Location(),
    @SerializedName("name") var name: String? = null,
    @SerializedName("related_places") var relatedPlaces: RelatedPlaces? = RelatedPlaces(),
    @SerializedName("timezone") var timezone: String? = null
) {
    data class Categories(
        @SerializedName("id") var id: Int? = null,
        @SerializedName("name") var name: String? = null,
        @SerializedName("icon") var icon: Icon? = Icon()
    )

    data class Icon(
        @SerializedName("prefix") var prefix: String? = null,
        @SerializedName("suffix") var suffix: String? = null
    )

    data class Geocodes(
        @SerializedName("main") var main: Main? = Main()
    ) {
        data class Main(
            @SerializedName("latitude") var latitude: Double? = null,
            @SerializedName("longitude") var longitude: Double? = null
        )
    }

    data class Location(
        @SerializedName("address") var address: String? = null,
        @SerializedName("admin_region") var adminRegion: String? = null,
        @SerializedName("country") var country: String? = null,
        @SerializedName("cross_street") var crossStreet: String? = null,
        @SerializedName("formatted_address") var formattedAddress: String? = null,
        @SerializedName("locality") var locality: String? = null,
        @SerializedName("neighborhood") var neighborhood: ArrayList<String> = arrayListOf(),
        @SerializedName("post_town") var postTown: String? = null,
        @SerializedName("postcode") var postcode: String? = null,
        @SerializedName("region") var region: String? = null
    )

    data class RelatedPlaces(
        @SerializedName("children") var children: ArrayList<Children> = arrayListOf()
    ) {
        data class Children(
            @SerializedName("fsq_id") var fsqId: String? = null,
            @SerializedName("name") var name: String? = null
        )
    }

}