package com.ew.suggestmeasong.models.search

import com.ew.suggestmeasong.models.toptracks.ImageModel
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class SearchResultsTrack : Serializable {

    @SerializedName("name")
    var name: String? = null

    @SerializedName("artist")
    var artist: String? = null

    @SerializedName("image")
    var images: ArrayList<ImageModel>? = null

    override fun toString(): String {
        return "SearchResultsTrack(name=$name, artist=$artist, images=$images)\n"
    }
}
