package com.ew.suggestmeasong.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ew.suggestmeasong.R
import com.ew.suggestmeasong.models.toptracks.TrackModel
import com.ew.suggestmeasong.utils.ImageSize
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.song_list_item.view.*

class SongsAdapter(val context: Context, val topTrackList: List<TrackModel>, val itemClick: (TrackModel) -> Unit)
    : RecyclerView.Adapter<SongsAdapter.Holder>() {

    companion object {
        const val LOG = "SongsAdapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.song_list_item, parent, false);
        return Holder(view, itemClick)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindCategory(topTrackList[position], context)
    }

    override fun getItemCount(): Int {
        return topTrackList.count()
    }

    inner class Holder(itemView: View?, val itemClick: (TrackModel) -> Unit) : RecyclerView.ViewHolder(itemView) {
        fun bindCategory(trackModel: TrackModel, context: Context) {
            itemView.tvTrackName.text = trackModel.name
            itemView.tvArtistName.text = trackModel.artist?.name
            val placeholderResId = R.drawable.ic_image_placeholder
            Picasso.with(context).load(trackModel.images?.get(ImageSize.MEDIUM.size)?.imgUrl)
                    .placeholder(placeholderResId)
                    .error(placeholderResId)
                    .into(itemView.ivTrackImage)
            itemView.setOnClickListener {
                itemClick(trackModel)
                Log.e(LOG, trackModel.toString())
            }
        }
    }
}