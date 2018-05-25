package com.ew.suggestmeasong

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.ew.suggestmeasong.adapters.SearchResultsAdapter
import com.ew.suggestmeasong.api.ApiService
import com.ew.suggestmeasong.models.search.SearchResultsResponse
import com.ew.suggestmeasong.models.search.SearchResultsTrack
import com.ew.suggestmeasong.utils.*
import kotlinx.android.synthetic.main.activity_results.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchResultsActivity : AppCompatActivity() {

    companion object {
        const val TAG = "SearchResultsActivity"
    }

    private lateinit var mSearchResultsAdapter: SearchResultsAdapter
    private lateinit var mSearchQuery: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        mSearchQuery = intent.getStringExtra(EXTRA_SEARCH_QUERY)
        toolbarSearchResults.title = getString(R.string.search_results_title, mSearchQuery)
        setSupportActionBar(toolbarSearchResults)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        searchTracks(mSearchQuery)
    }

    private fun searchTracks(searchQuery: String) {
        val apiService = ApiService.create()
        val call = apiService.searchTracks(apiKey = getString(R.string.last_fm_api_key), track = searchQuery)
        call.enqueue(object : Callback<SearchResultsResponse> {
            override fun onResponse(call: Call<SearchResultsResponse>, response: Response<SearchResultsResponse>?) {
                pbLoader.visibility = View.GONE
                tvEmptySearchResults.visibility = View.GONE
                if (response != null) {
                    val searchResults = response.body().resultsModel?.trackMatchesWrap?.tracks
                            ?: mutableListOf<SearchResultsTrack>()
                    if (searchResults.size > 0) {
                        setAdapter(searchResults)
                    } else {
                        tvEmptySearchResults.visibility = View.VISIBLE
                        tvEmptySearchResults.text = getString(R.string.search_results_nothing_found)
                    }
                }
            }

            override fun onFailure(call: Call<SearchResultsResponse>, t: Throwable) {
                pbLoader.visibility = View.GONE
                tvEmptySearchResults.visibility = View.VISIBLE
                tvEmptySearchResults.text = getString(R.string.search_results_nothing_found)
                if (!isNetworkConnected(this@SearchResultsActivity)) {
                    val message = getString(R.string.snackbar_no_internet_text)
                    val duration = Snackbar.LENGTH_INDEFINITE
                    showSnackbar(clRoot, message, duration)
                }
                Log.e(TAG, t.localizedMessage)
            }
        })
    }

    private fun setAdapter(searchResults: MutableList<SearchResultsTrack>) {
        mSearchResultsAdapter = SearchResultsAdapter(this, searchResults) { clickedTrack ->
            handleOnTrackClick(clickedTrack)
        }
        rvSearchResults.adapter = mSearchResultsAdapter
        val linearLayoutManager = LinearLayoutManager(this)
        rvSearchResults.layoutManager = linearLayoutManager
    }

    private fun handleOnTrackClick(clickedTrack: SearchResultsTrack) {
        val songDetailsIntent = Intent(this, SongDetailsActivity::class.java)
        songDetailsIntent.putExtra(EXTRA_DETAILS_TRACK_NAME, clickedTrack.name)
        songDetailsIntent.putExtra(EXTRA_DETAILS_ARTIST_NAME, clickedTrack.artist)
        songDetailsIntent.putExtra(EXTRA_DETAILS_TRACK_IMAGE, clickedTrack.images?.get(3)?.imgUrl)
        startActivity(songDetailsIntent)
    }

    fun showSnackbar(view: View, message: String, duration: Int) {
        val snackbar = Snackbar.make(view, message, duration)
        snackbar.setAction(getString(R.string.snackbar_btn_action_label), {
            searchTracks(mSearchQuery)
            tvEmptySearchResults.visibility = View.GONE
            pbLoader.visibility = View.VISIBLE
        })
        snackbar.show()
    }
}
