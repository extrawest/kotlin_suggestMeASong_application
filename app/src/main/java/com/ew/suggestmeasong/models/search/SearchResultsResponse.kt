package com.ew.suggestmeasong.models.search

import com.google.gson.annotations.SerializedName

class SearchResultsResponse {

    @SerializedName("results")
    var resultsModel : ResultsModel? = null
}