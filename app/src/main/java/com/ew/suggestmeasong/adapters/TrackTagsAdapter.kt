package com.ew.suggestmeasong.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ew.suggestmeasong.R
import com.ew.suggestmeasong.models.trackinfo.Tag
import kotlinx.android.synthetic.main.song_tag_item.view.*


class TrackTagsAdapter(val context: Context, val tagList: List<Tag>) : RecyclerView.Adapter<TrackTagsAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.song_tag_item, parent, false);
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindCategory(tagList[position])
    }

    override fun getItemCount(): Int {
        return tagList.count()
    }

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        fun bindCategory(trackModel: Tag) {
            itemView.tvTag.text = trackModel.name
        }
    }
}