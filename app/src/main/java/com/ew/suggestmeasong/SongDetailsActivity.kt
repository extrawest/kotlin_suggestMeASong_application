package com.ew.suggestmeasong

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.ew.suggestmeasong.adapters.TrackTagsAdapter
import com.ew.suggestmeasong.api.ApiService
import com.ew.suggestmeasong.models.trackinfo.Tag
import com.ew.suggestmeasong.models.trackinfo.TrackInfoResponse
import com.ew.suggestmeasong.utils.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_song_details.*
import kotlinx.android.synthetic.main.content_song_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SongDetailsActivity : AppCompatActivity() {
    companion object {
        const val TAG = "SongDetailsActivity"
    }

    lateinit var trackName: String
    lateinit var artistName: String
    lateinit var imageUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_details)

        trackName = intent.getStringExtra(EXTRA_DETAILS_TRACK_NAME)
        artistName = intent.getStringExtra(EXTRA_DETAILS_ARTIST_NAME)
        imageUrl = intent.getStringExtra(EXTRA_DETAILS_TRACK_IMAGE)

        fab.setOnClickListener({ findSimilarSongsActivity(trackName, artistName) })
        btnViewSimilar.setOnClickListener({ findSimilarSongsActivity(trackName, artistName) })

        val placeholderResId = R.drawable.ic_image_placeholder
        if (imageUrl.isBlank()) {
            imageUrl = PLACEHOLDER_URL
        }
        Picasso.with(this).load(imageUrl)
                .placeholder(placeholderResId)
                .error(placeholderResId)
                .into(ivTrackDetailsBackground)
        toolbar.title = artistName

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        getTrackInfo(this, artistName, trackName)
    }

    private fun getTrackInfo(context: Context, artist: String, trackName: String) {
        val apiService = ApiService.create()
        val call = apiService.getTrackInfo(artist = artist, track = trackName, apiKey = getString(R.string.last_fm_api_key))
        call.enqueue(object : Callback<TrackInfoResponse> {
            override fun onResponse(call: Call<TrackInfoResponse>, response: Response<TrackInfoResponse>?) {
                pbLoader.visibility = View.GONE
                if (response != null) {
                    val trackTags = response.body().track?.toptags?.tags ?: mutableListOf<Tag>()
                    val trackInfo = response.body().track?.info?.content ?: ""

                    val content = cleanUpContent(trackInfo)
                    if (content.isEmpty()) {
                        tvTrackWiki.text = getString(R.string.empty_details_text)
                    } else {
                        tvTrackWiki.text = content
                    }
                    tvTrackTitle.text = trackName
                    btnViewSimilar.visibility = View.VISIBLE
                    // setting up tag adapter
                    val tagsAdapter = TrackTagsAdapter(context, trackTags)
                    val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    rvTrackTags.layoutManager = layoutManager
                    rvTrackTags.adapter = tagsAdapter
                }
            }

            override fun onFailure(call: Call<TrackInfoResponse>, t: Throwable) {
                Log.e(TAG, t.localizedMessage)
                tvTrackWiki.text = getString(R.string.snackbar_no_internet_text)
                if (!isNetworkConnected(context)) {
                    val message = getString(R.string.snackbar_no_internet_text)
                    val duration = Snackbar.LENGTH_INDEFINITE
                    showSnackbar(context, clRoot, message, duration)
                }
                pbLoader.visibility = View.GONE
            }
        })
    }

    private fun findSimilarSongsActivity(trackName: String, artistName: String) {
        val searchResultsIntent = Intent(this, SimilarSongsActivity::class.java)
        searchResultsIntent.putExtra(EXTRA_SIMILAR_SONGS_TRACK_NAME, trackName)
        searchResultsIntent.putExtra(EXTRA_SIMILAR_SONGS_ARTIST_NAME, artistName)
        startActivity(searchResultsIntent)
    }

    fun showSnackbar(context: Context, view: View, message: String, duration: Int) {
        val snackbar = Snackbar.make(view, message, duration)
        snackbar.setAction(getString(R.string.snackbar_btn_action_label), {
            getTrackInfo(context, artistName, trackName)
            tvTrackWiki.text = ""
            pbLoader.visibility = View.VISIBLE
        })
        snackbar.show()
    }
}
