package com.ew.suggestmeasong.api

import com.ew.suggestmeasong.models.search.SearchResultsResponse
import com.ew.suggestmeasong.models.similartracks.SimilarTracksResponse
import com.ew.suggestmeasong.models.toptracks.TopTracksResponse
import com.ew.suggestmeasong.models.trackinfo.TrackInfoResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("?method=$CHART_TOP_TRACKS")
    fun getTopTracks(
            @Query("limit") limit: Int = DEFAULT_TRACKS_LIMIT,
            @Query("api_key") apiKey: String,
            @Query("format") format: String = DEFAULT_APP_FORMAT
    ): Call<TopTracksResponse>

    @GET("?method=$TRACK_INFO")
    fun getTrackInfo(
            @Query("api_key") apiKey: String,
            @Query("artist") artist: String,
            @Query("track") track: String,
            @Query("format") format: String = DEFAULT_APP_FORMAT
    ): Call<TrackInfoResponse>

    @GET("?method=$SEARCH_TRACKS")
    fun searchTracks(
            @Query("api_key") apiKey: String,
            @Query("limit") limit: Int = DEFAULT_TRACKS_LIMIT,
            @Query("track") track: String,
            @Query("format") format: String = DEFAULT_APP_FORMAT
    ): Call<SearchResultsResponse>


    @GET("?method=$FIND_SIMILAR_TRACKS")
    fun getSimilarTracks(
            @Query("api_key") apiKey: String,
            @Query("artist") artist: String,
            @Query("track") track: String,
            @Query("limit") limit: Int = DEFAULT_TRACKS_LIMIT,
            @Query("format") format: String = DEFAULT_APP_FORMAT
    ): Call<SimilarTracksResponse>

    companion object Factory {
        fun create(): ApiService {
            val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}