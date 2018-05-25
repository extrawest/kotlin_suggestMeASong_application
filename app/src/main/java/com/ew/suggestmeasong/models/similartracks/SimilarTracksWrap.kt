package com.ew.suggestmeasong.models.similartracks

import com.ew.suggestmeasong.models.toptracks.TrackModel
import com.google.gson.annotations.SerializedName

class SimilarTracksWrap {

    @SerializedName("track")
    var similarTracks: ArrayList<TrackModel>? = null

    override fun toString(): String {
        return "SimilarTracksWrap(similarTracks=$similarTracks)\n"
    }

}