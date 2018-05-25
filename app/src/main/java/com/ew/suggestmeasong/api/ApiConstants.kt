package com.ew.suggestmeasong.api

const val BASE_URL = "http://ws.audioscrobbler.com/2.0/"

const val LIMIT_PER_PAGE = 10
const val DEFAULT_TRACKS_LIMIT = 10
const val DEFAULT_APP_FORMAT = "json"

const val CHART_TOP_TRACKS = "chart.gettoptracks"
const val TRACK_INFO = "track.getInfo"
const val SEARCH_TRACKS = "track.search"
const val FIND_SIMILAR_TRACKS = "track.getSimilar"