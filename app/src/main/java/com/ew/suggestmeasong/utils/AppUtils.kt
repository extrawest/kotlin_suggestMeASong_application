package com.ew.suggestmeasong.utils

import android.content.Context
import android.net.ConnectivityManager


const val EXTRA_DETAILS_TRACK_NAME = "EXTRA_DETAILS_TRACK_NAME"
const val EXTRA_DETAILS_ARTIST_NAME = "EXTRA_DETAILS_ARTIST_NAME"
const val EXTRA_DETAILS_TRACK_IMAGE = "EXTRA_DETAILS_TRACK_IMAGE"
const val EXTRA_SEARCH_QUERY = "EXTRA_SEARCH_QUERY"
const val EXTRA_SIMILAR_SONGS_TRACK_NAME = "EXTRA_SIMILAR_SONGS_TRACK_NAME"
const val EXTRA_SIMILAR_SONGS_ARTIST_NAME = "EXTRA_SIMILAR_SONGS_ARTIST_NAME"

const val PLACEHOLDER_URL = "https://lastfm-img2.akamaized.net/i/u/174s/4128a6eb29f94943c9d206c08e625904"

enum class ImageSize(val size: Int) {
    SMALL(0), MEDIUM(1), LARGE(2), EXTRA_LARGE(3), MEGA(4)
}

fun cleanUpContent(content: String): String {
    val linksStartWith = "<a href"
    if (content.contains(linksStartWith)) {
        return content.substring(0, content.indexOf(linksStartWith))
    }
    return content
}


fun isNetworkConnected(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val ni = cm.activeNetworkInfo
    return ni != null
}
