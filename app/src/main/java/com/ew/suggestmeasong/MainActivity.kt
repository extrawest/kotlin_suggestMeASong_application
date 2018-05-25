package com.ew.suggestmeasong

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.arlib.floatingsearchview.FloatingSearchView
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion
import com.ew.suggestmeasong.adapters.SongsAdapter
import com.ew.suggestmeasong.api.ApiService
import com.ew.suggestmeasong.models.toptracks.TopTracksResponse
import com.ew.suggestmeasong.models.toptracks.TrackModel
import com.ew.suggestmeasong.utils.*
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
    }

    private lateinit var mSongsAdapter: SongsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbarMain)

        getTopTracks()

        floatingSearchView.setOnSearchListener(object : FloatingSearchView.OnSearchListener {
            override fun onSuggestionClicked(searchSuggestion: SearchSuggestion) {}
            override fun onSearchAction(currentQuery: String?) {
                if (currentQuery != null && currentQuery.trim { it <= ' ' }.length >= 2) {
                    if (floatingSearchView.isSearchBarFocused) {
                        floatingSearchView.clearSearchFocus()
                    }
                    startSearchResultsActivity(currentQuery)
                }
            }
        })
    }

    override fun onBackPressed() {
        if (floatingSearchView.isSearchBarFocused) {
            floatingSearchView.clearSearchFocus();
        } else {
            super.onBackPressed();
        }
    }

    private fun getTopTracks() {
        val apiService = ApiService.create()
        val call = apiService.getTopTracks(apiKey = getString(R.string.last_fm_api_key))
        call.enqueue(object : Callback<TopTracksResponse> {
            override fun onResponse(call: Call<TopTracksResponse>, response: Response<TopTracksResponse>?) {
                pbLoader.visibility = View.GONE
                tvEmptyTopTracks.visibility = View.GONE
                if (response != null) {
                    val topTrackList = response.body().tracksWrapper?.tracks
                            ?: mutableListOf<TrackModel>()
                    if (topTrackList.size > 0) {
                        setAdapter(topTrackList)
                    } else {
                        tvEmptyTopTracks.text = getString(R.string.empty_top_tracks_text)
                        tvEmptyTopTracks.visibility = View.VISIBLE
                    }
                }
            }

            override fun onFailure(call: Call<TopTracksResponse>, t: Throwable) {
                pbLoader.visibility = View.GONE
                tvEmptyTopTracks.visibility = View.VISIBLE
                tvEmptyTopTracks.text = getString(R.string.no_internet_text)
                if (!isNetworkConnected(this@MainActivity)) {
                    val message = getString(R.string.snackbar_no_internet_text)
                    val duration = Snackbar.LENGTH_INDEFINITE
                    showSnackbar(clRoot, message, duration)
                }
                Log.e(TAG, t.localizedMessage)
            }
        })
    }

    fun showSnackbar(view: View, message: String, duration: Int) {
        val snackbar = Snackbar.make(view, message, duration)
        snackbar.setAction(getString(R.string.snackbar_btn_action_label), {
            getTopTracks()
            tvEmptyTopTracks.visibility = View.GONE
            pbLoader.visibility = View.VISIBLE
        })
        snackbar.show()
    }

    private fun setAdapter(topTrackList: MutableList<TrackModel>) {
        mSongsAdapter = SongsAdapter(this, topTrackList) { clickedTrack ->
            floatingSearchView.clearSearchFocus()
            handleOnTrackClick(clickedTrack)
        }
        rvTopTracks.adapter = mSongsAdapter
        val linearLayoutManager = LinearLayoutManager(this)
        rvTopTracks.layoutManager = linearLayoutManager
    }

    private fun handleOnTrackClick(clickedTrack: TrackModel) {
        val songDetailsIntent = Intent(this, SongDetailsActivity::class.java)
        songDetailsIntent.putExtra(EXTRA_DETAILS_TRACK_NAME, clickedTrack.name)
        songDetailsIntent.putExtra(EXTRA_DETAILS_ARTIST_NAME, clickedTrack.artist?.name)
        songDetailsIntent.putExtra(EXTRA_DETAILS_TRACK_IMAGE, clickedTrack.images?.get(ImageSize.EXTRA_LARGE.size)?.imgUrl)

        startActivity(songDetailsIntent)
    }

    private fun startSearchResultsActivity(searchQuery: String) {
        val searchResultsIntent = Intent(this, SearchResultsActivity::class.java)
        searchResultsIntent.putExtra(EXTRA_SEARCH_QUERY, searchQuery)
        startActivity(searchResultsIntent)
    }
}
