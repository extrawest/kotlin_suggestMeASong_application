package com.ew.suggestmeasong.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ew.suggestmeasong.R
import com.ew.suggestmeasong.models.search.SearchResultsTrack
import com.ew.suggestmeasong.utils.ImageSize
import com.ew.suggestmeasong.utils.PLACEHOLDER_URL
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.song_list_item.view.*

class SearchResultsAdapter(val context: Context, val searchResults: List<SearchResultsTrack>, val itemClick: (SearchResultsTrack) -> Unit)
    : RecyclerView.Adapter<SearchResultsAdapter.Holder>() {

    companion object {
        const val LOG = "SearchResultsAdapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.song_list_item, parent, false);
        return Holder(view, itemClick)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindCategory(searchResults[position], context)
    }

    override fun getItemCount(): Int {
        return searchResults.count()
    }

    inner class Holder(itemView: View?, val itemClick: (SearchResultsTrack) -> Unit) : RecyclerView.ViewHolder(itemView) {
        fun bindCategory(trackModel: SearchResultsTrack, context: Context) {
            itemView.tvTrackName.text = trackModel.name
            itemView.tvArtistName.text = trackModel.artist
            val placeholderResId = R.drawable.ic_image_placeholder
            var imgUrl = trackModel.images?.get(ImageSize.MEDIUM.size)?.imgUrl
            if (imgUrl.isNullOrBlank()) {
                imgUrl = PLACEHOLDER_URL
            }
            Picasso.with(context).load(imgUrl)
                    .placeholder(placeholderResId)
                    .error(placeholderResId)
                    .into(itemView.ivTrackImage)
            itemView.setOnClickListener { itemClick(trackModel) }
        }
    }
}