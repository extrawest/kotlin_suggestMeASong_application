package com.ew.suggestmeasong.models.search

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ResultsModel : Serializable {

    @SerializedName("trackmatches")
    var trackMatchesWrap: TracksMatchesWrap? = null
}