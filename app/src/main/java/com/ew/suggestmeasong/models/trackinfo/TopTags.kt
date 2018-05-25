package com.ew.suggestmeasong.models.trackinfo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class TopTags : Serializable {

    @SerializedName("tag")
    var tags: ArrayList<Tag>? = null

    override fun toString(): String {
        return "TopTags(tags=$tags)\n"
    }
}