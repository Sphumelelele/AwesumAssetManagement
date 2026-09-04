package com.awesum.assetmanagement.ui.photo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.awesum.assetmanagement.R
import com.awesum.assetmanagement.model.SitePhoto

class PhotoAdapter(private var photos: List<SitePhoto> = emptyList()) :
    RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    fun submitList(newList: List<SitePhoto>) {
        photos = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false)
        return PhotoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(photos[position])
    }

    override fun getItemCount(): Int = photos.size

    class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivPhoto: ImageView = itemView.findViewById(R.id.ivPhoto)
        private val tvCaption: TextView = itemView.findViewById(R.id.tvCaption)

        fun bind(photo: SitePhoto) {
            Glide.with(itemView.context)
                .load(photo.file_url)
                .centerCrop()
                .into(ivPhoto)

            if (!photo.caption.isNullOrBlank()) {
                tvCaption.text = photo.caption
                tvCaption.visibility = View.VISIBLE
            } else {
                tvCaption.visibility = View.GONE
            }
        }
    }
}
