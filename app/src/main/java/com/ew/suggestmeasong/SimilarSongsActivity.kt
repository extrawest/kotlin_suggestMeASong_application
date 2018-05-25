package com.ew.suggestmeasong

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.ew.suggestmeasong.adapters.SongsAdapter
import com.ew.suggestmeasong.api.ApiService
import com.ew.suggestmeasong.models.similartracks.SimilarTracksResponse
import com.ew.suggestmeasong.models.toptracks.TrackModel
import com.ew.suggestmeasong.utils.*
import kotlinx.android.synthetic.main.activity_similar_songs.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SimilarSongsActivity : AppCompatActivity() {

    companion object {
        const val TAG = "SimilarSongsActivity"
    }

    private lateinit var trackName: String
    private lateinit var artistName: String
    private lateinit var mSongsAdapter: SongsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_similar_songs)

        trackName = intent.getStringExtra(EXTRA_SIMILAR_SONGS_TRACK_NAME)
        artistName = intent.getStringExtra(EXTRA_SIMILAR_SONGS_ARTIST_NAME)

        toolbarSimilarTracks.title = getString(R.string.similar_songs_title, trackName)
        setSupportActionBar(toolbarSimilarTracks)
        toolbarSimilarTracks.setNavigationOnClickListener({ finish() })
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        getSimilarTracks(trackName, artistName)
    }

    private fun getSimilarTracks(trackName: String, artistName: String) {
        val apiService = ApiService.create()
        val call = apiService.getSimilarTracks(apiKey = getString(R.string.last_fm_api_key), track = trackName, artist = artistName)
        call.enqueue(object : Callback<SimilarTracksResponse> {
            override fun onResponse(call: Call<SimilarTracksResponse>, response: Response<SimilarTracksResponse>?) {
                pbLoader.visibility = View.GONE
                tvEmptyView.visibility = View.GONE
                if (response != null) {
                    val trackList = response.body().trackWrapper?.similarTracks
                            ?: mutableListOf<TrackModel>()
                    if (trackList.size > 0) {
                        setAdapter(trackList)
                    } else {
                        tvEmptyView.text = getString(R.string.no_similar_songs_text)
                        tvEmptyView.visibility = View.VISIBLE
                    }
                }
            }

            override fun onFailure(call: Call<SimilarTracksResponse>, t: Throwable) {
                pbLoader.visibility = View.GONE
                tvEmptyView.visibility = View.VISIBLE
                tvEmptyView.text = getString(R.string.no_internet_text)
                if (!isNetworkConnected(this@SimilarSongsActivity)) {
                    val message = getString(R.string.snackbar_no_internet_text)
                    val duration = Snackbar.LENGTH_INDEFINITE
                    showSnackbar(clRoot, message, duration)
                }
                Log.e(TAG, t.localizedMessage)
            }
        })
    }

    private fun setAdapter(trackList: MutableList<TrackModel>) {
        mSongsAdapter = SongsAdapter(this, trackList) { clickedTrack ->
            handleOnTrackClick(clickedTrack)
        }
        rvSimilarTracks.adapter = mSongsAdapter
        val linearLayoutManager = LinearLayoutManager(this)
        rvSimilarTracks.layoutManager = linearLayoutManager
    }

    private fun handleOnTrackClick(clickedTrack: TrackModel) {
        val songDetailsIntent = Intent(this, SongDetailsActivity::class.java)
        songDetailsIntent.putExtra(EXTRA_DETAILS_TRACK_NAME, clickedTrack.name)
        songDetailsIntent.putExtra(EXTRA_DETAILS_ARTIST_NAME, clickedTrack.artist?.name)
        songDetailsIntent.putExtra(EXTRA_DETAILS_TRACK_IMAGE, clickedTrack.images?.get(ImageSize.EXTRA_LARGE.size)?.imgUrl)

        startActivity(songDetailsIntent)
    }

    fun showSnackbar(view: View, message: String, duration: Int) {
        val snackbar = Snackbar.make(view, message, duration)
        snackbar.setAction(getString(R.string.snackbar_btn_action_label), {
            getSimilarTracks(trackName, artistName)
            tvEmptyView.visibility = View.GONE
            pbLoader.visibility = View.VISIBLE
        })
        snackbar.show()
    }
}
