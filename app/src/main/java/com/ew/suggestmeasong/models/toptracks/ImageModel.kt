package com.ew.suggestmeasong.models.toptracks

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ImageModel : Serializable {

    @SerializedName("#text")
    var imgUrl: String? = null

    @SerializedName("size")
    var size: String? = null

    override fun toString(): String {
        return "ImageModel(imgUrl=$imgUrl, size=$size)\n"
    }
}
