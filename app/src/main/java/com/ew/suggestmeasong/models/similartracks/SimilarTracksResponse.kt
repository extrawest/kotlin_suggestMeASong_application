package com.ew.suggestmeasong.models.similartracks

import com.google.gson.annotations.SerializedName

class SimilarTracksResponse {

    @SerializedName("similartracks")
    var trackWrapper: SimilarTracksWrap? = null
}