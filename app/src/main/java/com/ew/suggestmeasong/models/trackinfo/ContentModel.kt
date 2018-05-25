package com.ew.suggestmeasong.models.trackinfo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ContentModel : Serializable {

    @SerializedName("content")
    var content: String? = null

    override fun toString(): String {
        return "ContentModel(content=$content)\n"
    }


}
