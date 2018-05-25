package com.ew.suggestmeasong.models.toptracks

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ArtistModel : Serializable {

    @SerializedName("name")
    var name: String? = null

    @SerializedName("url")
    var artistUrl: String? = null

    override fun toString(): String {
        return "ArtistModel(name=$name, artistUrl=$artistUrl)\n"
    }

}
