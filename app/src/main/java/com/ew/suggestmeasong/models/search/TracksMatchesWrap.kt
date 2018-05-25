package com.ew.suggestmeasong.models.search

import com.google.gson.annotations.SerializedName

class TracksMatchesWrap {

    @SerializedName("track")
    var tracks : ArrayList<SearchResultsTrack>? = null
}
