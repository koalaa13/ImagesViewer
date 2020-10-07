package com.example.imagesviewer

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*
import java.util.concurrent.ConcurrentHashMap

class ImageAdapter(
    private val images: List<Image>,
    private val imagesMap: ConcurrentHashMap<String, Bitmap>,
    private val onClick: (Image) -> Unit
) :
    RecyclerView.Adapter<ImageViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val holder = ImageViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        )
        holder.root.setOnClickListener {
            onClick(images[holder.adapterPosition])
        }
        return holder
    }

    override fun onViewRecycled(holder: ImageViewHolder) {
        holder.root.thumb_image.setImageDrawable(null)
        holder.root.thumb_image.visibility = View.GONE
        holder.root.thumb_image_downloading_progressbar.visibility = View.VISIBLE
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val curImage = images[position]
        DownloadImage(
            imagesMap,
            holder.root.thumb_image,
            listOf(holder.root.thumb_image_downloading_progressbar)
        ).execute(curImage.thumbLink)
        if (curImage.title != null) {
            holder.root.image_title.text = curImage.title
        }
    }

    override fun getItemCount(): Int {
        return images.size
    }
}