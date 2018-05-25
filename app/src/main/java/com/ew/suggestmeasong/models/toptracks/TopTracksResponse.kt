package com.ew.suggestmeasong.models.toptracks

import com.google.gson.annotations.SerializedName

class TopTracksResponse {

    @SerializedName("tracks")
    var tracksWrapper: Tracks? = null
}