package com.ew.suggestmeasong.models.trackinfo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class TrackInfoModel : Serializable {

    @SerializedName("toptags")
    var toptags: TopTags? = null

    @SerializedName("wiki")
    var info: ContentModel? = null

    override fun toString(): String {
        return "TrackInfoModel(toptags=$toptags, info=$info)\n"
    }
}