package com.ew.suggestmeasong.models.toptracks

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Tracks : Serializable {
    @SerializedName("track")
    var tracks : ArrayList<TrackModel>? = null
}
