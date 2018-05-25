package com.ew.suggestmeasong.models.toptracks

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class TrackModel : Serializable {

    @SerializedName("name")
    var name: String? = null

    @SerializedName("artist")
    var artist: ArtistModel? = null

    @SerializedName("image")
    var images: ArrayList<ImageModel>? = null

    @SerializedName("post_count")
    var post_count: Int = 0

    override fun toString(): String {
        return "TrackModel(name=$name, artist=$artist, images=$images, post_count=$post_count)\n"
    }


}