package com.ew.suggestmeasong.models.trackinfo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Tag : Serializable {

    @SerializedName("name")
    var name: String? = null

    @SerializedName("url")
    var url: String? = null

    override fun toString(): String {
        return "Tag(name=$name, url=$url)\n"
    }
}